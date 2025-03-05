package com.musinsa.store.product.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.musinsa.store.common.cache.CacheStorage;
import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;
import com.musinsa.store.product.domain.dto.SearchOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSearchService {
  private final CacheStorage cacheStorage;
  private final ProductRepository productRepository;
  private final Set<String> usingCacheKeys = new HashSet<>();
  private final Integer cacheTtl = 0;
  
  public ProductSet searchSet(SearchOrder order) {
    log.info("search set by {}", order);
    String cacheKey = String.format("searchSet-{}", order);

    return useCache(cacheKey, ProductSet.class, () -> {
      return productRepository.findSet(order);
    });
  }

  public ProductSet searchSetForSingleBrand(SearchOrder order) {
    log.info("search set for single brand by {}", order);
    String cacheKey = String.format("searchSetForSingleBrand-{}", order);

    return useCache(cacheKey, ProductSet.class, () -> {
      return productRepository.findSetForSingleBrand(order);
    });
  }

  @SuppressWarnings("unchecked")
  public CompletableFuture<Optional<ProductDto>> searchCategory(Category category, SearchOrder order) {
    return CompletableFuture.supplyAsync(() -> {
      log.info("search category({}) by {}", category, order);
      String cacheKey = String.format("searchCategory-{}-{}", category, order);

      return useCache(cacheKey, Optional.class, () -> {
        return productRepository.findBy(category, order);
      });
    });
  }

  private <T> T useCache(String key, Class<T> clazz, Supplier<T> supplier) {
    return cacheStorage.get(key, clazz)
        .orElseGet(() -> {
          final T value = supplier.get();
          CompletableFuture.runAsync(() -> {
            synchronized (usingCacheKeys) {
              usingCacheKeys.add(key);
              cacheStorage.put(key, supplier.get(), cacheTtl);
            }
          });
          return value;
        });
  }

  public void clearCache() {
    synchronized(usingCacheKeys) {
      List<String> cacheKeys = usingCacheKeys.stream().toList();
      usingCacheKeys.clear();
      for (String cacheKey : cacheKeys) {
        cacheStorage.remove(cacheKey);
      }
    }
  }

}
