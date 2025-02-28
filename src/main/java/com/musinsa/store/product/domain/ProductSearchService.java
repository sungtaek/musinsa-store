package com.musinsa.store.product.domain;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSearchService {
  private final ProductRepository productRepository;
  
  public List<Product> getLowestPricedSet() {
    log.info("get lowest priced set");

    return productRepository.getLowestPricedSet();
  }

  public List<Product> getLowestPricedSetForSingleBrand() {
    log.info("get lowest priced set for single brand");

    return productRepository.getLowestPricedSetForSingleBrand();
  }

  public CompletableFuture<Optional<Product>> getLowestPricedBy(Category category) {
    return CompletableFuture.supplyAsync(() -> {
      log.info("get lowest priced by {}", category);

      return productRepository.getLowestPricedBy(category);
    });
  }

  public CompletableFuture<Optional<Product>> getHighestPricedBy(Category category) {
    return CompletableFuture.supplyAsync(() -> {
      log.info("get highest priced by {}", category);

      return productRepository.getHighestPricedBy(category);
    });
  }
}
