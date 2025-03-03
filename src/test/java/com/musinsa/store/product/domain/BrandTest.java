package com.musinsa.store.product.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BrandTest {
  
  @Test
  @DisplayName("카테고리 검사")
  public void checkCategory() {
    Brand brand = Brand.builder()
        .products(List.of(
            Product.builder().category(Category.TOPS).build(),
            Product.builder().category(Category.OUTER).build(),
            Product.builder().category(Category.PANTS).build(),
            Product.builder().category(Category.SNEAKERS).build()))
        .build();

    assertFalse(brand.checkCategory());

    brand = Brand.builder()
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

    assertTrue(brand.checkCategory());
  }

  @Test
  @DisplayName("상품 id set 비교")
  public void checkProductIds() {
    Brand brand = Brand.builder()
        .products(List.of(
            Product.builder().id(1L).category(Category.TOPS).build(),
            Product.builder().id(2L).category(Category.OUTER).build(),
            Product.builder().id(3L).category(Category.PANTS).build()))
        .build();

    assertTrue(brand.hasSameProducts(Brand.builder()
        .products(List.of(
            Product.builder().id(1L).category(Category.TOPS).build(),
            Product.builder().id(2L).category(Category.OUTER).build(),
            Product.builder().id(3L).category(Category.PANTS).build()))
        .build()));

    assertFalse(brand.hasSameProducts(Brand.builder()
        .products(List.of(
            Product.builder().id(1L).category(Category.TOPS).build(),
            Product.builder().id(3L).category(Category.PANTS).build()))
        .build()));

    assertFalse(brand.hasSameProducts(Brand.builder()
        .products(List.of(
            Product.builder().id(1L).category(Category.TOPS).build(),
            Product.builder().id(1L).category(Category.TOPS).build(),
            Product.builder().id(3L).category(Category.PANTS).build()))
        .build()));
  }
}
