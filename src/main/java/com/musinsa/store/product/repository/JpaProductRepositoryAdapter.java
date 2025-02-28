package com.musinsa.store.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.musinsa.store.common.exception.DatabaseException;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.Product;
import com.musinsa.store.product.domain.ProductRepository;
import com.musinsa.store.product.repository.entity.ProductEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaProductRepositoryAdapter implements ProductRepository {

  private final JpaProductRepository repository;

  @Override
  public List<Product> getLowestPricedSet() {
    try {
      return repository.findLowestPricedSet().stream()
          .map(ProductEntity::toProduct).toList();
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public List<Product> getLowestPricedSetForSingleBrand() {
    try {
      return repository.findLowestPricedSetForSingleBrand().stream()
          .map(ProductEntity::toProduct).toList();
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public Optional<Product> getLowestPricedBy(Category category) {
    try {
      return repository.findFirstByCategoryOrderByPriceAsc(category)
          .map(ProductEntity::toProduct);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public Optional<Product> getHighestPricedBy(Category category) {
    try {
      return repository.findFirstByCategoryOrderByPriceDesc(category)
          .map(ProductEntity::toProduct);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }
  
}
