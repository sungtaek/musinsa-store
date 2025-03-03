package com.musinsa.store.product.domain.dto;

import com.musinsa.store.product.domain.Brand;

import lombok.Builder;
import lombok.Data;
import lombok.Builder.Default;

@Data
@Builder
public class BrandDto {
  private Long id;
  private String name;
  @Default
  private ProductSet products = new ProductSet();

  public Brand toBrand() {
    return Brand.builder()
        .id(id)
        .name(name)
        .products(products.stream()
            .map(ProductDto::toProduct)
            .toList())
        .build();
  }

  public static BrandDto from(Brand brand) {
    return BrandDto.builder()
        .id(brand.getId())
        .name(brand.getName())
        .products(ProductSet.of(brand.getProducts(), ProductDto::from))
        .build();
  }
}
