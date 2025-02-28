package com.musinsa.store.product.repository.entity;

import com.musinsa.store.common.jpa.BaseEntity;
import com.musinsa.store.product.domain.Brand;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.Product;

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
@Table(name = "product", indexes = {
  @Index(columnList = "category, price"),
  @Index(columnList = "category, id")
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

  public Product toProduct() {
    return Product.builder()
        .id(id)
        .brand(Brand.builder()
            .id(brand.getId())
            .name(brand.getName())
            .build())
        .category(category)
        .price(price)
        .build();
  }
}
