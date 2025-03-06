package com.musinsa.store.product.domain;

import java.util.Optional;

import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;
import com.musinsa.store.product.domain.dto.PriceOrder;

public interface ProductRepository {
  ProductSet findSet(PriceOrder priceOrder);
  ProductSet findSetForSingleBrand(PriceOrder priceOrder);
  Optional<ProductDto> findBy(Category category, PriceOrder priceOrder);
}
