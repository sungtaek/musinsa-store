package com.musinsa.store.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  private Long id;
  private Brand brand;
  private Category category;
  private Integer price;
}
