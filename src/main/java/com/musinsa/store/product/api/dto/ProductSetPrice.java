package com.musinsa.store.product.api.dto;

public enum ProductSetPrice {
  LOWEST,
  HIGHEST;

  public boolean isLowest() {
    return (this == LOWEST);
  }

  public boolean isHighest() {
    return (this == HIGHEST);
  }
}
