package com.musinsa.store.product.domain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSearchService {
  private final ProductRepository productRepository;
  
  public List<Product> getLowestPricedSet() {
    // TODO
    return Collections.emptyList();
  }

  public List<Product> getLowestPricedSetForSingleBrand() {
    // TODO
    return Collections.emptyList();
  }

  public CompletableFuture<Optional<Product>> getLowestPricedBy(Category category) {
    // TODO
    return CompletableFuture.completedFuture(Optional.empty());
  }

  public CompletableFuture<Optional<Product>> getHighestPricedBy(Category category) {
    // TODO
    return CompletableFuture.completedFuture(Optional.empty());
  }
}
