package com.musinsa.store.product.domain;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSearchService {
  private final ProductRepository productRepository;
  
  public ProductSet getLowestPricedSet() {
    log.info("get lowest priced set");

    return productRepository.getLowestPricedSet();
  }

  public ProductSet getLowestPricedSetForSingleBrand() {
    log.info("get lowest priced set for single brand");

    return productRepository.getLowestPricedSetForSingleBrand();
  }

  public CompletableFuture<Optional<ProductDto>> getLowestPricedBy(Category category) {
    return CompletableFuture.supplyAsync(() -> {
      log.info("get lowest priced by category({})", category);

      return productRepository.getLowestPricedBy(category);
    });
  }

  public CompletableFuture<Optional<ProductDto>> getHighestPricedBy(Category category) {
    return CompletableFuture.supplyAsync(() -> {
      log.info("get highest priced by category({})", category);

      return productRepository.getHighestPricedBy(category);
    });
  }
}
