package com.musinsa.store.product.domain;

import java.util.Optional;

import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;

public interface ProductRepository {
  ProductSet getLowestPricedSet();
  ProductSet getLowestPricedSetForSingleBrand();
  Optional<ProductDto> getLowestPricedBy(Category category);
  Optional<ProductDto> getHighestPricedBy(Category category);
}
