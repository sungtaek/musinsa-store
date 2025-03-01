package com.musinsa.store.product.api.dto;

import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductPayload {
  private Long id;
  private Category category;
  private Integer price;

  public static ProductPayload of(Product product) {
    if (product == null) {
      return null;
    }
    return ProductPayload.builder()
        .id(product.getId())
        .category(product.getCategory())
        .price(product.getPrice())
        .build();
  }

  public Product toProduct() {
    return Product.builder()
        .id(id)
        .category(category)
        .price(price)
        .build();
  }
}
