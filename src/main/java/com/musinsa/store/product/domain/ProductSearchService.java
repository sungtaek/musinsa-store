package com.musinsa.store.product.domain;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;
import com.musinsa.store.product.domain.dto.SearchOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSearchService {
  private final ProductRepository productRepository;
  
  public ProductSet searchSet(SearchOrder order) {
    log.info("search set by {}", order);

    return productRepository.findSet(order);
  }

  public ProductSet searchSetForSingleBrand(SearchOrder order) {
    log.info("search set for single brand by {}", order);

    return productRepository.findSetForSingleBrand(order);
  }

  public CompletableFuture<Optional<ProductDto>> searchCategory(Category category, SearchOrder order) {
    return CompletableFuture.supplyAsync(() -> {
      log.info("search category({}) by {}", category, order);

      return productRepository.findBy(category, order);
    });
  }

}
