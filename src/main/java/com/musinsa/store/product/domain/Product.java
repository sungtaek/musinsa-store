package com.musinsa.store.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  private Long id;
  private Brand brand;
  private Category category;
  private Integer price;
}
