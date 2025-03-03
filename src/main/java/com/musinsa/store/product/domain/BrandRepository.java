package com.musinsa.store.product.domain;

import java.util.Optional;

import com.musinsa.store.common.dto.Page;

public interface BrandRepository {
  Page<Brand> list(int page, int size);
  Brand save(Brand brand);
  boolean isExist(Long id);
  Optional<Brand> get(Long id);
  void delete(Long id);
}
