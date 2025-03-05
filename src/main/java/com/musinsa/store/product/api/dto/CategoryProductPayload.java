package com.musinsa.store.product.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.dto.ProductDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryProductPayload {
  private Category category;
  private String brandName;
  private Integer price;

  public static CategoryProductPayload from(ProductDto product) {
    if (product == null) {
      return null;
    }
    return CategoryProductPayload.builder()
        .category(product.getCategory())
        .brandName(product.getBrandName())
        .price(product.getPrice())
        .build();
  }
}
