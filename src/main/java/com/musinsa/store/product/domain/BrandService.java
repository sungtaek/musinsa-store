package com.musinsa.store.product.domain;

import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.musinsa.store.common.dto.Page;
import com.musinsa.store.common.event.BrandEvent;
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
  private final ApplicationEventPublisher eventPublisher;

  public Page<BrandDto> list(int page, int size) {
    log.info("list: page({}) size({})", page, size);

    return brandRepository.list(page, size);
  }

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

    brandDto = brandRepository.save(BrandDto.from(brand));
    eventPublisher.publishEvent(BrandEvent.CREATED);
    return brandDto;
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
      brandDto = brandRepository.save(BrandDto.from(brand));
      eventPublisher.publishEvent(BrandEvent.UPDATED);
      return Optional.of(brandDto);
    } else {
      return Optional.empty();
    }
  }

  @Transactional
  public void delete(Long id) {
    log.info("delete: {}", id);

    brandRepository.delete(id);
    eventPublisher.publishEvent(BrandEvent.DELETED);
  }

}
