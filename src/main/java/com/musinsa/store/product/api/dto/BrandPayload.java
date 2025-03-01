package com.musinsa.store.product.api.dto;

import java.util.List;

import com.musinsa.store.product.domain.Brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandPayload {
  private Long id;
  private String name;
  private List<ProductPayload> products;
  
  public static BrandPayload of(Brand brand) {
    if (brand == null) {
      return null;
    }
    return BrandPayload.builder()
        .id(brand.getId())
        .name(brand.getName())
        .products(brand.getProductSet().stream()
            .map(ProductPayload::of)
            .toList())
        .build();
  }

  public Brand toBrand() {
    return Brand.builder()
        .id(id)
        .name(name)
        .products(products.stream()
            .map(ProductPayload::toProduct)
            .toList())
        .build();
  }
}
