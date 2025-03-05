package com.musinsa.store.common.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LocalCacheStorageTest {
  private static String TEST_KEY = "key";
  private static String TEST_VALUE = "value";
  
  @Test
  @DisplayName("캐시 Hit")
  public void hit() {
    LocalCacheStorage cache = new LocalCacheStorage();

    cache.put(TEST_KEY, TEST_VALUE);

    Optional<String> value = cache.get(TEST_KEY, String.class);

    assertTrue(value.isPresent());
    assertEquals(TEST_VALUE, value.get());
  }

  @Test
  @DisplayName("캐시 Miss")
  public void miss() {
    LocalCacheStorage cache = new LocalCacheStorage();

    Optional<String> value = cache.get("unknown", String.class);

    assertFalse(value.isPresent());
  }

  @Test
  @DisplayName("캐시 Expire")
  public void expire() throws InterruptedException {
    LocalCacheStorage cache = new LocalCacheStorage();

    cache.put(TEST_KEY, TEST_VALUE, 1);

    Optional<String> value = cache.get(TEST_KEY, String.class);

    assertTrue(value.isPresent());
    assertEquals(TEST_VALUE, value.get());

    Thread.sleep(2000);

    value = cache.get(TEST_KEY, String.class);

    assertFalse(value.isPresent());
  }

  @Test
  @DisplayName("캐시 삭제")
  public void remove() {
    LocalCacheStorage cache = new LocalCacheStorage();

    cache.put(TEST_KEY, TEST_VALUE);

    Optional<String> value = cache.get(TEST_KEY, String.class);

    assertTrue(value.isPresent());
    assertEquals(TEST_VALUE, value.get());

    cache.remove(TEST_KEY);

    value = cache.get(TEST_KEY, String.class);

    assertFalse(value.isPresent());
  }
}
