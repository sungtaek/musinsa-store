package com.musinsa.store.product.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.store.product.domain.Product;
import com.musinsa.store.product.domain.ProductSet;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryProductSetPayload {
  private String brandName;
  private List<CategoryProductPayload> products;
  private Integer totalPrice;

  public static CategoryProductSetPayload of(Product product) {
    return CategoryProductSetPayload.builder()
        .products(List.of(CategoryProductPayload.of(product)))
        .build();
  }

  public static CategoryProductSetPayload of(ProductSet productSet) {
    return CategoryProductSetPayload.builder()
        .products(productSet.stream()
            .map(CategoryProductPayload::of)
            .toList())
        .totalPrice(productSet.getTotalPrice())
        .build();
  }
  
  public static CategoryProductSetPayload of(String brandName, ProductSet productSet) {
    CategoryProductSetPayload payload = of(productSet);
    payload.setBrandName(brandName);
    return payload;
  }

}
