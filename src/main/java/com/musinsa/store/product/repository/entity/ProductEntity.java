package com.musinsa.store.product.repository.entity;

import com.musinsa.store.common.jpa.BaseEntity;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.dto.ProductDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product", uniqueConstraints = {
    @UniqueConstraint(name = "brand_category", columnNames = { "brand_id", "category" })
}, indexes = {
    @Index(name = "category_price", columnList = "category, price"),
    @Index(name = "category_id", columnList = "category, id")
})
public class ProductEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "brand_id", nullable = false)
  private BrandEntity brand;

  @Column(name = "category", nullable = false)
  @Enumerated(EnumType.STRING)
  private Category category;

  @Column(name = "price", nullable = false)
  private Integer price;

  public ProductDto toProduct() {
    return ProductDto.builder()
        .id(id)
        .brandId(brand.getId())
        .brandName(brand.getName())
        .category(category)
        .price(price)
        .build();
  }

  public ProductDto toProductWithoutBrand() {
    return ProductDto.builder()
        .id(id)
        .category(category)
        .price(price)
        .build();
  }
}
