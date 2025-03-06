package com.musinsa.store.product.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.musinsa.store.common.exception.DatabaseException;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.ProductRepository;
import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;
import com.musinsa.store.product.domain.dto.PriceOrder;
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
  public ProductSet findSet(PriceOrder priceOrder) {
    QProductEntity product = QProductEntity.productEntity;
    QProductEntity product2 = new QProductEntity("product2");
    QProductEntity product3 = new QProductEntity("product3");

    NumberExpression<Integer> priceExpression;
    if (priceOrder == PriceOrder.LOWEST) {
      priceExpression = product3.price.min();
    } else if (priceOrder == PriceOrder.HIGHEST) {
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
  public ProductSet findSetForSingleBrand(PriceOrder priceOrder) {
    QProductEntity product = QProductEntity.productEntity;
    QBrandEntity brand = QBrandEntity.brandEntity;
    QBrandEntity brand2 = new QBrandEntity("brand2");

    NumberExpression<Integer> priceExpression;
    if (priceOrder == PriceOrder.LOWEST) {
      priceExpression = brand2.totalPrice.min();
    } else if (priceOrder == PriceOrder.HIGHEST) {
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
  public Optional<ProductDto> findBy(Category category, PriceOrder priceOrder) {
    QProductEntity product = QProductEntity.productEntity;

    OrderSpecifier<Integer> order;
    if (priceOrder == PriceOrder.LOWEST) {
      order = product.price.asc();
    } else if (priceOrder == PriceOrder.HIGHEST) {
      order = product.price.desc();
    } else {
      order = product.price.asc();
    }
    try {
      return Optional.ofNullable(queryFactory
          .selectFrom(product)
          .where(product.category.eq(category))
          .orderBy(order, product.id.desc())
          .fetchFirst()).map(ProductEntity::toProduct);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

}
