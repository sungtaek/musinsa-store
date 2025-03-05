package com.musinsa.store.product.api;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musinsa.store.common.dto.ResponsePayload;
import com.musinsa.store.product.api.dto.CategoryProductSetPayload;
import com.musinsa.store.product.api.dto.ResultProductSetPayload;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.ProductSearchService;
import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;
import com.musinsa.store.product.domain.dto.SearchOrder;

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
    CategoryProductSetPayload lowestPayload = null;

    if(singleBrand) {
      ProductSet productSet = productSearchService.searchSetForSingleBrand(SearchOrder.LOWEST_PRICE);
      lowestPayload = CategoryProductSetPayload.from(getFristBrandName(productSet), productSet);
    } else {
      ProductSet productSet = productSearchService.searchSet(SearchOrder.LOWEST_PRICE);
      lowestPayload = CategoryProductSetPayload.from(productSet);
    }

    return ResponsePayload.<ResultProductSetPayload>builder()
        .data(ResultProductSetPayload.builder()
            .lowestPrice(lowestPayload)
            .build())
        .build();
  }

  private String getFristBrandName(ProductSet productSet) {
    return productSet.stream()
        .filter(p -> p.getBrandName() != null)
        .findFirst()
        .map(ProductDto::getBrandName)
        .orElse(null);
  }

  @GetMapping("/lowest-highest-category")
  public ResponsePayload<ResultProductSetPayload> getLowestHighestPricedCategory(
      @RequestParam(value = "category") Category category) throws Throwable {
    log.info("get lowest/highest priced: category({})", category);
    CategoryProductSetPayload lowestPayload = null;
    CategoryProductSetPayload highestPayload = null;

    CompletableFuture<Optional<ProductDto>> lowestProduct = productSearchService
        .searchCategory(category, SearchOrder.LOWEST_PRICE);
    CompletableFuture<Optional<ProductDto>> highestProduct = productSearchService
        .searchCategory(category, SearchOrder.HIGHEST_PRICE);

    try {
      lowestPayload = CategoryProductSetPayload.from(lowestProduct.get().orElse(null));
      highestPayload = CategoryProductSetPayload.from(highestProduct.get().orElse(null));
    } catch (Exception ex) {
      throwUnwrapedException(ex);
    }

    return ResponsePayload.<ResultProductSetPayload>builder()
        .data(ResultProductSetPayload.builder()
            .category(category)
            .lowestPrice(lowestPayload)
            .highestPrice(highestPayload)
            .build())
        .build();
  }

  private void throwUnwrapedException(Exception ex) throws Exception {
    if (ex instanceof ExecutionException) {
      throw (Exception) ex.getCause();
    }
    throw ex;
  }

}
