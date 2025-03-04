package com.musinsa.store.product.domain;

import java.util.Optional;

import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;
import com.musinsa.store.product.domain.dto.SearchOrder;

public interface ProductRepository {
  ProductSet findSet(SearchOrder order);
  ProductSet findSetForSingleBrand(SearchOrder order);
  Optional<ProductDto> findBy(Category category, SearchOrder order);
}
