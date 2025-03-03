package com.musinsa.store.product.domain;

import java.util.Optional;
import com.musinsa.store.common.dto.Page;
import com.musinsa.store.product.domain.dto.BrandDto;

public interface BrandRepository {
  Page<BrandDto> list(int page, int size);
  BrandDto save(BrandDto brand);
  boolean isExist(Long id);
  Optional<BrandDto> get(Long id);
  void delete(Long id);
}
