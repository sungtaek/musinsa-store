package com.musinsa.store.product.repository.entity;

import java.util.List;

import com.musinsa.store.common.jpa.BaseEntity;
import com.musinsa.store.product.domain.Brand;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
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
@Table(name = "brand", indexes = {
    @Index(name = "total_price", columnList = "total_price")
})
public class BrandEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductEntity> products;

  @Column(name = "total_price", nullable = false)
  private Integer totalPrice;

  public static BrandEntity of(Brand brand) {
    if (brand == null) {
      return null;
    }
    BrandEntity brandEntity = BrandEntity.builder()
        .id(brand.getId())
        .name(brand.getName())
        .totalPrice(brand.getProductSet().getTotalPrice())
        .build();
    brandEntity.setProducts(brand.getProductSet().stream()
        .map(p -> ProductEntity.builder()
            .id(p.getId())
            .brand(brandEntity)
            .category(p.getCategory())
            .price(p.getPrice())
            .build())
        .toList());
    return brandEntity;
  }

  public Brand toBrand() {
    return Brand.builder()
        .id(id)
        .name(name)
        .products(products.stream()
            .map(ProductEntity::toProduct)
            .toList())
        .build();
  }

  public Brand toBrandWithoutProducts() {
    return Brand.builder()
        .id(id)
        .name(name)
        .build();
  }
}
