package com.musinsa.store.product.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.store.product.domain.Brand;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryProductPayload {
  private Category category;
  private String brandName;
  private Integer price;

  public static CategoryProductPayload of(Product product) {
    Brand brand = product.getBrand();
    return CategoryProductPayload.builder()
        .category(product.getCategory())
        .brandName((brand != null) ? brand.getName() : null)
        .price(product.getPrice())
        .build();
  }
}
