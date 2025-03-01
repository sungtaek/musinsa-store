package com.musinsa.store.product.domain;

import java.util.Collection;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

@Data
@NoArgsConstructor
public class Brand {
  private Long id;
  private String name;
  @Setter(AccessLevel.NONE)
  private ProductSet productSet = new ProductSet();

  @Builder
  public Brand(Long id, String name, List<Product> products) {
    this.id = id;
    this.name = name;
    addProducts(products);
  }

  public void addProduct(Product product) {
    if (product != null) {
      productSet.add(product.toBuilder().brand(null).build());
    }
  }

  public void addProducts(Collection<Product> products) {
    if (products != null) {
      for (Product product : products) {
        addProduct(product);
      }
    }
  }

  public boolean checkCategory() {
    return (productSet.hasAllCategories() && productSet.size() == Category.values().length);
  }

}
