package com.musinsa.store.common.cache;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class LocalCacheStorage implements CacheStorage {

  private final ConcurrentHashMap<String, Node> storage = new ConcurrentHashMap<>();

  private static class Node {
    private Object data;
    private long expires;

    private Node(Object data) {
      this.data = data;
      this.expires = Long.MAX_VALUE;
    }

    private Node(Object data, long expires) {
      this.data = data;
      this.expires = expires;
    }
  }

  @Override
  public void put(String key, Object value) {
    storage.put(key, new Node(value));
  }

  @Override
  public void put(String key, Object value, int ttl) {
    if (ttl > 0) {
      storage.put(key, new Node(value, System.currentTimeMillis() + (ttl * 1000)));
    } else {
      storage.put(key, new Node(value));
    }
  }

  @Override
  public <T> Optional<T> get(String key, Class<T> clazz) {
    Node node = storage.get(key);
    if (node != null && node.expires > System.currentTimeMillis()) {
      return Optional.of(clazz.cast(node.data));
    } else {
      return Optional.empty();
    }
  }

  @Override
  public void remove(String key) {
    storage.remove(key);
  }
  
}
