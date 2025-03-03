package com.musinsa.store.product.domain.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

public class ProductSet extends ArrayList<ProductDto> {
  public ProductSet() {
    super();
  }
  public ProductSet(Collection<ProductDto> products) {
    super(products);
  }

  public Integer getTotalPrice() {
    return stream()
        .map(ProductDto::getPrice)
        .mapToInt(Integer::intValue)
        .sum();
  }

  public static ProductSet of(ProductDto... products) {
    return new ProductSet(Arrays.asList(products));
  }

  public static ProductSet of(Collection<ProductDto> products) {
    return new ProductSet(products);
  }

  public static <T> ProductSet of(Collection<T> data, Function<T, ProductDto> converter) {
    return new ProductSet(data.stream().map(converter).toList());
  }
}
