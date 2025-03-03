package com.musinsa.store.product.domain;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.musinsa.store.product.domain.dto.BrandDto;
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
  public BrandDto create(BrandDto brandDto) {
    log.info("create: {}", brandDto);
    Brand brand = brandDto.toBrand();

    // unset id
    brand.clearIds();

    // check category
    if (!brand.checkCategory()) {
      throw new InvalidBrandException("Brand should have one product per categories");
    }

    return brandRepository.save(BrandDto.from(brand));
  }

  public Optional<BrandDto> get(Long id) {
    log.info("get: {}", id);

    return brandRepository.get(id);
  }

  @Transactional
  public Optional<BrandDto> update(BrandDto brandDto) {
    log.info("update: {}", brandDto);
    Brand brand = brandDto.toBrand();

    // check id
    if (!brand.checkIds()) {
      throw new InvalidBrandException("Brand and all products should have id");
    }

    // check category
    if (!brand.checkCategory()) {
      throw new InvalidBrandException("Brand should hava one product per all categories");
    }

    // check current and save
    Optional<BrandDto> curBrandDto = brandRepository.get(brand.getId());
    if (curBrandDto.isPresent()) {
      Brand curBrand = curBrandDto.get().toBrand();
      if (!curBrand.hasSameProducts(brand)) {
        throw new InvalidBrandException("Product set is different from the current, please check the product's id");
      }
      return Optional.of(brandRepository.save(BrandDto.from(brand)));
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
