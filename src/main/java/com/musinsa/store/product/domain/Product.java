package com.musinsa.store.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
  private Long id;
  private Brand brand;
  private Category category;
  private Integer price;
}
