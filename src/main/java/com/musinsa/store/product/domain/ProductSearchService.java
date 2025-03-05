package com.musinsa.store.product.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import com.musinsa.store.common.cache.CacheStorage;
import com.musinsa.store.common.event.BrandEvent;
import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;
import com.musinsa.store.product.domain.dto.SearchOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSearchService {
  private final CacheStorage cacheStorage;
  private final ProductRepository productRepository;
  private final CacheProperties cacheProperties;
  private final Set<String> usingCacheKeys = new HashSet<>();

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @ConfigurationProperties("cache")
  public static class CacheProperties {
    private Boolean active;
    private Integer ttl;
  }

  public ProductSet searchSet(SearchOrder order) {
    log.info("search set by {}", order);

    if (cacheProperties.active) {
      String cacheKey = String.format("searchSet-%s", order);
      return useCache(cacheKey, ProductSet.class, () -> {
        return productRepository.findSet(order);
      });
    } else {
      return productRepository.findSet(order);
    }
  }

  public ProductSet searchSetForSingleBrand(SearchOrder order) {
    log.info("search set for single brand by {}", order);

    if (cacheProperties.active) {
      String cacheKey = String.format("searchSetForSingleBrand-%s", order);
      return useCache(cacheKey, ProductSet.class, () -> {
        return productRepository.findSetForSingleBrand(order);
      });
    } else {
      return productRepository.findSetForSingleBrand(order);
    }
  }

  @SuppressWarnings("unchecked")
  public CompletableFuture<Optional<ProductDto>> searchCategory(Category category, SearchOrder order) {
    return CompletableFuture.supplyAsync(() -> {
      log.info("search category({}) by {}", category, order);

      if (cacheProperties.active) {
        String cacheKey = String.format("searchCategory-%s-%s", category, order);
        return useCache(cacheKey, Optional.class, () -> {
          return productRepository.findBy(category, order);
        });
      } else {
        return productRepository.findBy(category, order);
      }
    });
  }

  private <T> T useCache(String key, Class<T> clazz, Supplier<T> supplier) {
    return cacheStorage.get(key, clazz)
        .map(v -> {
          log.debug("cache hit: {}", key);
          return v;
        })
        .orElseGet(() -> {
          log.debug("cache miss: {}", key);
          final T value = supplier.get();
          CompletableFuture.runAsync(() -> {
            synchronized (usingCacheKeys) {
              usingCacheKeys.add(key);
              cacheStorage.put(key, supplier.get(), cacheProperties.ttl);
            }
          });
          return value;
        });
  }

  @TransactionalEventListener
  public void onEvent(BrandEvent brandEvent) {
    log.info("receive event({})", brandEvent);

    // clear cache
    if (cacheProperties.active) {
      log.info("clear cache");
      synchronized (usingCacheKeys) {
        List<String> cacheKeys = usingCacheKeys.stream().toList();
        usingCacheKeys.clear();
        for (String cacheKey : cacheKeys) {
          cacheStorage.remove(cacheKey);
        }
      }
    }
  }

}
