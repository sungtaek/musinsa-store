package com.musinsa.store.product.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.musinsa.store.common.exception.DatabaseException;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.ProductRepository;
import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;
import com.musinsa.store.product.domain.dto.SearchOrder;
import com.musinsa.store.product.repository.entity.ProductEntity;
import com.musinsa.store.product.repository.entity.QBrandEntity;
import com.musinsa.store.product.repository.entity.QProductEntity;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QJpaProductRepository implements ProductRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public ProductSet findSet(SearchOrder order) {
    QProductEntity product = QProductEntity.productEntity;
    QProductEntity product2 = new QProductEntity("product2");
    QProductEntity product3 = new QProductEntity("product3");

    NumberExpression<Integer> priceExpression;
    if (order == SearchOrder.LOWEST_PRICE) {
      priceExpression = product3.price.min();
    } else if (order == SearchOrder.HIGHEST_PRICE) {
      priceExpression = product3.price.max();
    } else {
      priceExpression = product2.price.min();
    }

    // SELECT p
    // FROM ProductEntity p
    // WHERE p.id IN (
    //   SELECT MAX(p2.id)
    //   FROM ProductEntity p2
    //   WHERE p2.category = p.category
    //     AND p2.price = (
    //       SELECT MIN(p3.price)
    //       FROM ProductEntity p3
    //       WHERE p3.category = p2.category
    //     )
    //   GROUP BY p2.category
    // )

    try {
      return ProductSet.of(queryFactory
          .selectFrom(product)
          .where(product.id.in(queryFactory
              .select(product2.id.max())
              .from(product2)
              .where(product2.category.eq(product.category)
                  .and(product2.price.eq(queryFactory
                      .select(priceExpression)
                      .from(product3)
                      .where(product3.category.eq(product2.category)))))
              .groupBy(product2.category)))
          .fetch(), ProductEntity::toProduct);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public ProductSet findSetForSingleBrand(SearchOrder order) {
    QProductEntity product = QProductEntity.productEntity;
    QBrandEntity brand = QBrandEntity.brandEntity;
    QBrandEntity brand2 = new QBrandEntity("brand2");

    NumberExpression<Integer> priceExpression;
    if (order == SearchOrder.LOWEST_PRICE) {
      priceExpression = brand2.totalPrice.min();
    } else if (order == SearchOrder.HIGHEST_PRICE) {
      priceExpression = brand2.totalPrice.max();
    } else {
      priceExpression = brand2.totalPrice.min();
    }

    // SELECT p
    // FROM ProductEntity p
    // WHERE p.brand.id = (
    //   SELECT MAX(b.id)
    //   FROM BrandEntity b
    //   WHERE b.totalPrice = (
    //     SELECT MIN(b2.totalPrice)
    //     FROM BrandEntity b2
    //   )
    // ) 
    try {
      return ProductSet.of(queryFactory
          .selectFrom(product)
          .where(product.brand.id.eq(queryFactory
              .select(brand.id.max())
              .from(brand)
              .where(brand.totalPrice.eq(queryFactory
                  .select(priceExpression)
                  .from(brand2)))))
          .fetch(), ProductEntity::toProduct);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public Optional<ProductDto> findBy(Category category, SearchOrder order) {
    QProductEntity product = QProductEntity.productEntity;

    OrderSpecifier<Integer> priceOrder;
    if (order == SearchOrder.LOWEST_PRICE) {
      priceOrder = product.price.asc();
    } else if (order == SearchOrder.HIGHEST_PRICE) {
      priceOrder = product.price.desc();
    } else {
      priceOrder = product.price.asc();
    }
    try {
      return Optional.ofNullable(queryFactory
          .selectFrom(product)
          .where(product.category.eq(category))
          .orderBy(priceOrder)
          .fetchFirst()).map(ProductEntity::toProduct);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

}
