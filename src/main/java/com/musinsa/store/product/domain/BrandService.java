package com.musinsa.store.product.domain;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandService {
  private final BrandRepository brandRepository;

  public Brand create(Brand brand) {
    // TODO
    return brand;
  }

  public Brand get(Long id) {
    // TODO
    return null;
  }

  public Brand update(Brand brand) {
    // TODO
    return brand;
  }

  public void delete(Long id) {
    // TODO
  }
}
