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
  
  public ProductSet getLowestPricedSet() {
    log.info("get lowest priced set");

    List<Product> products = productRepository.getLowestPricedSet();

    return new ProductSet(products);
  }

  public ProductSet getLowestPricedSetForSingleBrand() {
    log.info("get lowest priced set for single brand");

    List<Product> products = productRepository.getLowestPricedSetForSingleBrand();

    return new ProductSet(products);
  }

  public CompletableFuture<Optional<Product>> getLowestPricedBy(Category category) {
    return CompletableFuture.supplyAsync(() -> {
      log.info("get lowest priced by category({})", category);

      return productRepository.getLowestPricedBy(category);
    });
  }

  public CompletableFuture<Optional<Product>> getHighestPricedBy(Category category) {
    return CompletableFuture.supplyAsync(() -> {
      log.info("get highest priced by category({})", category);

      return productRepository.getHighestPricedBy(category);
    });
  }
}
