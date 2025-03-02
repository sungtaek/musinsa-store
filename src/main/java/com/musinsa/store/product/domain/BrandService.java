package com.musinsa.store.product.domain;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.musinsa.store.product.exception.InvalidBrandException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService {
  private final BrandRepository brandRepository;

  @Transactional
  public Brand create(Brand brand) {
    log.info("create: {}", brand);

    // unset id
    brand.setId(null);
    for (Product product: brand.getProductSet()) {
      product.setId(null);
    }

    // check category
    if (!brand.checkCategory()) {
      throw new InvalidBrandException("Brand should have one product per categories");
    }

    return brandRepository.save(brand);
  }

  public Optional<Brand> get(Long id) {
    log.info("get: {}", id);

    return brandRepository.get(id);
  }

  @Transactional
  public Optional<Brand> update(Brand brand) {
    log.info("update: {}", brand);

    // check id
    if (brand.getId() == null) {
      throw new InvalidBrandException("Brand should have id");
    }
    for (Product product: brand.getProductSet()) {
      if (product.getId() == null) {
        throw new InvalidBrandException("All products should have id");
      }
    }

    // check category
    if (!brand.checkCategory()) {
      throw new InvalidBrandException("Brand should hava one product per all categories");
    }

    // check current and save
    Optional<Brand> curBrand = brandRepository.get(brand.getId());
    if (curBrand.isPresent()) {
      if (!curBrand.get().getProductSet().isSameProductSet(brand.getProductSet())) {
        throw new InvalidBrandException("Product set is different from the current, please check the product's id");
      }
      return Optional.of(brandRepository.save(brand));
    } else {
      return Optional.empty();
    }
  }

  @Transactional
  public void delete(Long id) {
    log.info("delete: {}", id);

    brandRepository.delete(id);
  }

}
