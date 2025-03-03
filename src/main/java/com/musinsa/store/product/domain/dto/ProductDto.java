package com.musinsa.store.product.domain.dto;

import com.musinsa.store.product.domain.Brand;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
  private Long id;
  private Long brandId;
  private String brandName;
  private Category category;
  private Integer price;

  public Product toProduct() {
    return Product.builder()
        .id(id)
        .brand(Brand.builder()
            .id(brandId)
            .name(brandName)
            .build())
        .category(category)
        .price(price)
        .build();
  }

  public static ProductDto from(Product product) {
    Brand brand = product.getBrand();
    return ProductDto.builder()
        .id(product.getId())
        .brandId(brand != null ? brand.getId() : null)
        .brandName(brand != null ? brand.getName() : null)
        .category(product.getCategory())
        .price(product.getPrice())
        .build();
  }
}
