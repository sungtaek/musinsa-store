package com.musinsa.store.product.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryProductSetPayload {
  private String brandName;
  private List<CategoryProductPayload> products;
  private Integer totalPrice;

  public static CategoryProductSetPayload from(ProductDto product) {
    return CategoryProductSetPayload.builder()
        .products(List.of(CategoryProductPayload.from(product)))
        .build();
  }

  public static CategoryProductSetPayload from(ProductSet productSet) {
    return CategoryProductSetPayload.builder()
        .products(productSet.stream()
            .map(CategoryProductPayload::from)
            .toList())
        .totalPrice(productSet.getTotalPrice())
        .build();
  }
  
  public static CategoryProductSetPayload from(String brandName, ProductSet productSet) {
    CategoryProductSetPayload payload = from(productSet);
    payload.setBrandName(brandName);
    return payload;
  }

}
