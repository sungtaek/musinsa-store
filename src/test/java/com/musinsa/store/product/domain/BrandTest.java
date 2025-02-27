package com.musinsa.store.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BrandTest {
  
  @Test
  @DisplayName("상품의 총액 계산")
  public void calcTotalPrice() {
    Brand brand = Brand.builder()
        .name("test")
        .products(List.of(
          Product.builder().price(1000).build(),
          Product.builder().price(2000).build(),
          Product.builder().price(3000).build(),
          Product.builder().price(2000).build()))
        .build();

    int total = brand.getTotalPrice();

    assertEquals(8000, total);
  }

  @Test
  @DisplayName("카테고리 검사")
  public void checkCategory() {
    Brand brand = Brand.builder()
        .name("test")
        .products(List.of(
            Product.builder().category(Category.TOPS).build(),
            Product.builder().category(Category.OUTER).build(),
            Product.builder().category(Category.PANTS).build(),
            Product.builder().category(Category.SNEAKERS).build()))
        .build();

    assertFalse(brand.hasAllCategories());

    brand = Brand.builder()
        .name("test")
        .products(List.of(
            Product.builder().category(Category.TOPS).build(),
            Product.builder().category(Category.OUTER).build(),
            Product.builder().category(Category.PANTS).build(),
            Product.builder().category(Category.SNEAKERS).build(),
            Product.builder().category(Category.BAGS).build(),
            Product.builder().category(Category.HATS).build(),
            Product.builder().category(Category.SOCKS).build(),
            Product.builder().category(Category.ACCESSORIES).build()))
        .build();

    assertTrue(brand.hasAllCategories());
  }
}
