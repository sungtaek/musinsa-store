package com.musinsa.store.product.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.store.product.domain.dto.BrandDto;
import com.musinsa.store.product.domain.dto.ProductSet;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandPayload {
  private Long id;
  @NotEmpty
  private String name;
  @NotNull
  @Valid
  private List<ProductPayload> products;
  
  public static BrandPayload from(BrandDto brand) {
    if (brand == null) {
      return null;
    }
    return BrandPayload.builder()
        .id(brand.getId())
        .name(brand.getName())
        .products(brand.getProducts().stream()
            .map(ProductPayload::from)
            .toList())
        .build();
  }

  public BrandDto toBrand() {
    return BrandDto.builder()
        .id(id)
        .name(name)
        .products(ProductSet.of(products.stream()
            .map(ProductPayload::toProduct)
            .toList()))
        .build();
  }
}
