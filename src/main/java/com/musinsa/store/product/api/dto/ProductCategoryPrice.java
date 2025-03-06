package com.musinsa.store.product.api.dto;

public enum ProductCategoryPrice {
  LOWEST,
  HIGHEST,
  LOWEST_HIGHEST;

  public boolean isLowest() {
    return (this == LOWEST || this == LOWEST_HIGHEST);
  }

  public boolean isHighest() {
    return (this == HIGHEST || this == LOWEST_HIGHEST);
  }
}
