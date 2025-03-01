package com.musinsa.store.product.api;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musinsa.store.common.dto.ResponsePayload;
import com.musinsa.store.common.exception.ServiceException;
import com.musinsa.store.product.api.dto.CategoryProductSetPayload;
import com.musinsa.store.product.api.dto.ResultProductSetPayload;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.Product;
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
    CategoryProductSetPayload lowestPayload = null;

    if(singleBrand) {
      ProductSet productSet = productSearchService.getLowestPricedSetForSingleBrand();
      lowestPayload = CategoryProductSetPayload.of(getFristBrandName(productSet), productSet);
    } else {
      ProductSet productSet = productSearchService.getLowestPricedSet();
      lowestPayload = CategoryProductSetPayload.of(productSet);
    }

    return ResponsePayload.<ResultProductSetPayload>builder()
        .data(ResultProductSetPayload.builder()
            .lowestPrice(lowestPayload)
            .build())
        .build();
  }

  private String getFristBrandName(ProductSet productSet) {
    return productSet.stream()
        .filter(p -> p.getBrand() != null)
        .findFirst()
        .map(p -> p.getBrand().getName())
        .get();
  }

  @GetMapping("/lowest-highest-category")
  public ResponsePayload<ResultProductSetPayload> getLowestHighestPriced(
      @RequestParam(value = "category") Category category) throws Throwable {
    log.info("get lowest/highest priced: category({})", category);
    CategoryProductSetPayload lowestPayload = null;
    CategoryProductSetPayload highestPayload = null;

    CompletableFuture<Optional<Product>> lowestProduct = productSearchService.getLowestPricedBy(category);
    CompletableFuture<Optional<Product>> highestProduct = productSearchService.getHighestPricedBy(category);

    try {
      lowestPayload = CategoryProductSetPayload.of(lowestProduct.get().get());
      highestPayload = CategoryProductSetPayload.of(highestProduct.get().get());
    } catch (Exception ex) {
      if (ex.getCause() instanceof ServiceException) {
        throw ex.getCause();
      } else {
        throw ex;
      }
    }

    return ResponsePayload.<ResultProductSetPayload>builder()
        .data(ResultProductSetPayload.builder()
            .category(category)
            .lowestPrice(lowestPayload)
            .highestPrice(highestPayload)
            .build())
        .build();
  }

}
