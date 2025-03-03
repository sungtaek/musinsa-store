package com.musinsa.store.product.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.dto.ProductDto;

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

  public static ProductPayload from(ProductDto product) {
    if (product == null) {
      return null;
    }
    return ProductPayload.builder()
        .id(product.getId())
        .category(product.getCategory())
        .price(product.getPrice())
        .build();
  }

  public ProductDto toProduct() {
    return ProductDto.builder()
        .id(id)
        .category(category)
        .price(price)
        .build();
  }
}
