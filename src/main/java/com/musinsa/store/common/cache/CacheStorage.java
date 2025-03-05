package com.musinsa.store.common.cache;

import java.util.Optional;

public interface CacheStorage {
  void put(String key, Object value);
  void put(String key, Object value, int ttl);
  <T> Optional<T> get(String key, Class<T> clazz);
  void remove(String key);
}
