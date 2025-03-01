package com.musinsa.store.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.repository.entity.ProductEntity;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
  
  @Query("""
    SELECT p
    FROM ProductEntity p
    WHERE p.id IN (
      SELECT MAX(p2.id)
      FROM ProductEntity p2
      WHERE p2.category = p.category
        AND p2.price = (
          SELECT MIN(p3.price)
          FROM ProductEntity p3
          WHERE p3.category = p2.category
        )
      GROUP BY p2.category
    ) """)
  List<ProductEntity> findLowestPricedSet();

  @Query("""
    SELECT p
    FROM ProductEntity p
    WHERE p.brand.id = (
      SELECT MAX(b.id)
      FROM BrandEntity b
      WHERE b.totalPrice = (
        SELECT MIN(b2.totalPrice)
        FROM BrandEntity b2
      )
    ) """)
  List<ProductEntity> findLowestPricedSetForSingleBrand();

  Optional<ProductEntity> findFirstByCategoryOrderByPriceAsc(Category category);

  Optional<ProductEntity> findFirstByCategoryOrderByPriceDesc(Category category);

}
