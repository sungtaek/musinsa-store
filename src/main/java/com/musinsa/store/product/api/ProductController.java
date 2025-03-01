package com.musinsa.store.product.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musinsa.store.common.dto.ResponsePayload;
import com.musinsa.store.product.api.dto.CategoryProductSetPayload;
import com.musinsa.store.product.api.dto.ResultProductSetPayload;
import com.musinsa.store.product.domain.ProductSearchService;
import com.musinsa.store.product.domain.ProductSet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
  private final ProductSearchService productSearchService;
  
  @GetMapping("/lowest-set")
  public ResponsePayload<ResultProductSetPayload> getLowestPricedSet(
      @RequestParam(value = "singleBrand", defaultValue = "false") Boolean singleBrand) {
    log.info("get lowest priced set: singleBrand({})", singleBrand);

    ProductSet productSet = null;
    String brandName = null;

    if(singleBrand) {
      productSet = productSearchService.getLowestPricedSetForSingleBrand();
      brandName = !productSet.isEmpty() ? productSet.get(0).getBrand().getName() : null;
    } else {
      productSet = productSearchService.getLowestPricedSet();
    }

    return ResponsePayload.<ResultProductSetPayload>builder()
        .data(ResultProductSetPayload.builder()
            .lowestPrice(CategoryProductSetPayload.of(brandName, productSet))
            .build())
        .build();
  }

}
