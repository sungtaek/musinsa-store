package com.musinsa.store.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductSetTest {
  
  @Test
  @DisplayName("상품의 총액 계산")
  public void calcTotalPrice() {
    ProductSet productSet = new ProductSet(List.of(
        Product.builder().price(1000).build(),
        Product.builder().price(2000).build(),
        Product.builder().price(3000).build(),
        Product.builder().price(2000).build()));

    int total = productSet.getTotalPrice();

    assertEquals(8000, total);
  }

  @Test
  @DisplayName("카테고리 검사")
  public void checkCategory() {
    ProductSet productSet = new ProductSet(List.of(
        Product.builder().category(Category.TOPS).build(),
        Product.builder().category(Category.OUTER).build(),
        Product.builder().category(Category.PANTS).build(),
        Product.builder().category(Category.SNEAKERS).build()));

    assertFalse(productSet.hasAllCategories());

    productSet = new ProductSet(List.of(
        Product.builder().category(Category.TOPS).build(),
        Product.builder().category(Category.OUTER).build(),
        Product.builder().category(Category.PANTS).build(),
        Product.builder().category(Category.SNEAKERS).build(),
        Product.builder().category(Category.BAGS).build(),
        Product.builder().category(Category.HATS).build(),
        Product.builder().category(Category.SOCKS).build(),
        Product.builder().category(Category.ACCESSORIES).build()));

    assertTrue(productSet.hasAllCategories());
  }

  @Test
  @DisplayName("상품 id set 비교")
  public void checkProductIds() {
    ProductSet productSet = new ProductSet(List.of(
        Product.builder().id(1L).category(Category.TOPS).build(),
        Product.builder().id(2L).category(Category.OUTER).build(),
        Product.builder().id(3L).category(Category.PANTS).build()));

    assertTrue(productSet.isSameProductSet(new ProductSet(List.of(
        Product.builder().id(1L).category(Category.TOPS).build(),
        Product.builder().id(2L).category(Category.OUTER).build(),
        Product.builder().id(3L).category(Category.PANTS).build()))));

    assertFalse(productSet.isSameProductSet(new ProductSet(List.of(
        Product.builder().id(1L).category(Category.TOPS).build(),
        Product.builder().id(3L).category(Category.PANTS).build()))));

    assertFalse(productSet.isSameProductSet(new ProductSet(List.of(
        Product.builder().id(1L).category(Category.TOPS).build(),
        Product.builder().id(1L).category(Category.TOPS).build(),
        Product.builder().id(3L).category(Category.PANTS).build()))));
  }
}
