package com.musinsa.store.product.domain;

import java.util.Optional;

public interface BrandRepository {
  Brand save(Brand brand);
  boolean isExist(Long id);
  Optional<Brand> get(Long id);
  void delete(Long id);
}
