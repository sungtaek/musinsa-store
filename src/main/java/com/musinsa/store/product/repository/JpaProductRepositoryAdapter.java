package com.musinsa.store.product.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.musinsa.store.common.exception.DatabaseException;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.ProductRepository;
import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;
import com.musinsa.store.product.repository.entity.ProductEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaProductRepositoryAdapter implements ProductRepository {

  private final JpaProductRepository repository;

  @Override
  public ProductSet getLowestPricedSet() {
    try {
      return ProductSet.of(repository.findLowestPricedSet(),
          ProductEntity::toProduct);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public ProductSet getLowestPricedSetForSingleBrand() {
    try {
      return ProductSet.of(repository.findLowestPricedSetForSingleBrand(),
          ProductEntity::toProduct);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public Optional<ProductDto> getLowestPricedBy(Category category) {
    try {
      return repository.findFirstByCategoryOrderByPriceAsc(category)
          .map(ProductEntity::toProduct);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public Optional<ProductDto> getHighestPricedBy(Category category) {
    try {
      return repository.findFirstByCategoryOrderByPriceDesc(category)
          .map(ProductEntity::toProduct);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }
  
}
