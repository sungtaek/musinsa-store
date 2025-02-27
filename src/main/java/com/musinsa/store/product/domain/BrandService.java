package com.musinsa.store.product.domain;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.musinsa.store.product.exception.InvalidBrandException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService {
  private final BrandRepository brandRepository;

  public Brand create(Brand brand) {
    log.info("create: {}", brand);

    if (brand.getId() != null) {
      throw new InvalidBrandException("New brand should not have id");
    }
    if (!brand.hasAllCategories()) {
      throw new InvalidBrandException("Missing some categories");
    }

    return brandRepository.save(brand);
  }

  public Optional<Brand> get(Long id) {
    log.info("get: {}", id);

    return brandRepository.get(id);
  }

  public Brand update(Brand brand) {
    log.info("update: {}", brand);

    if (brand.getId() == null) {
      throw new InvalidBrandException("Brand should have id");
    }
    if (!brand.hasAllCategories()) {
      throw new InvalidBrandException("Missing some categories");
    }

    if (!brandRepository.isExist(brand.getId())) {
      throw new InvalidBrandException("Brand not found");
    }
    return brandRepository.save(brand);
  }

  public void delete(Long id) {
    log.info("delete: {}", id);

    brandRepository.delete(id);
  }

}
