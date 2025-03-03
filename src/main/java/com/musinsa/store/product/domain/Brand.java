package com.musinsa.store.product.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder
public class Brand {
  private static List<Category> CATEGORIES = List.of(Category.values());

  private Long id;
  private String name;
  @Default
  private List<Product> products = new ArrayList<>();

  public Brand(Long id, String name, List<Product> products) {
    this.id = id;
    this.name = name;
    this.products = new ArrayList<>();
    addProducts(products);
  }

  public void addProduct(Product product) {
    if (product != null) {
      products.add(product.toBuilder().brand(this).build());
    }
  }

  public void addProducts(Collection<Product> products) {
    if (products != null) {
      for (Product product : products) {
        addProduct(product);
      }
    }
  }

  public void clearIds() {
    setId(null);
    for (Product product: products) {
      product.setId(null);
    }
  }

  public boolean checkIds() {
    if (getId() == null) {
      return false;
    }
    return products.stream()
        .allMatch(p -> p.getId() != null);
  }

  public boolean hasSameProducts(Brand other) {
    Set<Long> ids = products.stream().map(Product::getId).collect(Collectors.toSet());
    Set<Long> otherIds = other.getProducts().stream().map(Product::getId).collect(Collectors.toSet());
    return (ids.size() == otherIds.size() && ids.equals(otherIds));
  }

  public boolean checkCategory() {
    return (hasAllCategories() && products.size() == Category.values().length);
  }

  private boolean hasAllCategories() {
    HashSet<Category> marks = new HashSet<>(CATEGORIES);
    products.stream()
        .map(Product::getCategory)
        .forEach(marks::remove);
    return marks.isEmpty();
  }

}
