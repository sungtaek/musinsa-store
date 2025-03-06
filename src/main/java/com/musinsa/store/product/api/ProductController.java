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
import com.musinsa.store.product.api.dto.ProductCategoryPrice;
import com.musinsa.store.product.api.dto.ProductSetPrice;
import com.musinsa.store.product.api.dto.ResultProductSetPayload;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.ProductSearchService;
import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;
import com.musinsa.store.product.domain.dto.PriceOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
  private final ProductSearchService productSearchService;
  
  @GetMapping("/set")
  public ResponsePayload<ResultProductSetPayload> getSet(
      @RequestParam(value = "price", defaultValue = "LOWEST") ProductSetPrice price,
      @RequestParam(value = "singleBrand", defaultValue = "false") Boolean singleBrand) {
    log.info("get set: price({}) singleBrand({})", price, singleBrand);
    CategoryProductSetPayload productSetPayload = null;

    PriceOrder priceOrder = (price.isHighest())
        ? PriceOrder.HIGHEST
        : PriceOrder.LOWEST;

    if(singleBrand) {
      ProductSet productSet = productSearchService.searchSetForSingleBrand(priceOrder);
      productSetPayload = CategoryProductSetPayload.from(getFristBrandName(productSet), productSet);
    } else {
      ProductSet productSet = productSearchService.searchSet(priceOrder);
      productSetPayload = CategoryProductSetPayload.from(productSet);
    }

    return ResponsePayload.<ResultProductSetPayload>builder()
        .data(ResultProductSetPayload.builder()
            .lowestPrice((priceOrder == PriceOrder.LOWEST) ? productSetPayload : null)
            .highestPrice((priceOrder == PriceOrder.HIGHEST) ? productSetPayload : null)
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

  @GetMapping("/by-category")
  public ResponsePayload<ResultProductSetPayload> getOne(
      @RequestParam(value = "category") Category category,
      @RequestParam(value = "price", defaultValue = "LOWEST") ProductCategoryPrice price) throws Throwable {
    log.info("get lowest/highest priced: category({})", category);

    CompletableFuture<Optional<ProductDto>> lowestProduct = null;
    CompletableFuture<Optional<ProductDto>> highestProduct = null;
    if (price.isLowest()) {
      lowestProduct = productSearchService
          .searchCategory(category, PriceOrder.LOWEST);
    }
    if (price.isHighest()) {
      highestProduct = productSearchService
          .searchCategory(category, PriceOrder.HIGHEST);
    }

    CategoryProductSetPayload lowestPayload = null;
    CategoryProductSetPayload highestPayload = null;
    try {
      if (lowestProduct != null) {
        lowestPayload = CategoryProductSetPayload.from(lowestProduct.get().orElse(null));
      }
      if (highestProduct != null) {
        highestPayload = CategoryProductSetPayload.from(highestProduct.get().orElse(null));
      }
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
