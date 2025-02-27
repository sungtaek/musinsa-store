package com.musinsa.store.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
  List<Product> getLowestPricedSet();
  List<Product> getLowestPricedSetForSingleBrand();
  Optional<Product> getLowestPricedBy(Category category);
  Optional<Product> getHighestPricedBy(Category category);
}
