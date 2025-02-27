package com.musinsa.store.product.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
  private Long id;
  private String name;
  @Default
  private List<Product> products = new ArrayList<>();
  private Integer totalPrice;

  public Integer getTotalPrice() {
    if (totalPrice == null) {
      totalPrice = products.stream()
          .map(Product::getPrice)
          .mapToInt(Integer::intValue)
          .sum();
    }
    return totalPrice;
  }
}
