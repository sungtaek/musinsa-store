package com.musinsa.store.product.domain;

import java.util.Optional;

public interface BrandRepository {
  Brand save(Brand brand);
  boolean exist(Long id);
  Optional<Brand> get(Long id);
}
