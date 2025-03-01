package com.musinsa.store.product.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.store.product.domain.Category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultProductSetPayload {
  private Category category;
  private CategoryProductSetPayload lowestPrice;
  private CategoryProductSetPayload highestPrice;
}
