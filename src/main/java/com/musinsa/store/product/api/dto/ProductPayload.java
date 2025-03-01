package com.musinsa.store.product.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductPayload {
  private Long id;
  @NotNull
  private Category category;
  @NotNull
  @Min(0)
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
