package com.musinsa.store.product.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class ProductSet extends ArrayList<Product> {
  private static List<Category> CATEGORIES = List.of(Category.values());

  public ProductSet() {
    super();
  }
  public ProductSet(Collection<Product> products) {
    super(products);
  }

  public Integer getTotalPrice() {
    return stream()
        .map(Product::getPrice)
        .mapToInt(Integer::intValue)
        .sum();
  }

  public boolean hasAllCategories() {
    HashSet<Category> marks = new HashSet<>(CATEGORIES);
    stream()
        .map(Product::getCategory)
        .forEach(marks::remove);
    return marks.isEmpty();
  }
}
