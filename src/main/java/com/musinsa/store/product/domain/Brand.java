package com.musinsa.store.product.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
  private Long id;
  private String name;
  @Default
  private ProductSet productSet = new ProductSet();

  public boolean checkCategory() {
    return (productSet.hasAllCategories() && productSet.size() == Category.values().length);
  }

}
