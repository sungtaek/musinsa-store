package com.musinsa.store.product.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.musinsa.store.common.exception.GlobalExceptionHandler;
import com.musinsa.store.common.exception.InternalException;
import com.musinsa.store.common.exception.ResultCode;
import com.musinsa.store.product.domain.Brand;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.Product;
import com.musinsa.store.product.domain.ProductSearchService;
import com.musinsa.store.product.domain.ProductSet;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
  private static final Long BRAND_ID = 1L;
  private static final String BRAND_NAME = "TEST_BRAND";
  private static final Long PRODUCT_ID = 10L;
  private static final Category PRODUCT_CATEGORY = Category.TOPS;
  private static final Integer PRODUCT_PRICE = 1000;

  private static final Long BRAND2_ID = 2L;
  private static final String BRAND2_NAME = "TEST_BRAND_2";
  private static final Long PRODUCT2_ID = 11L;
  private static final Category PRODUCT2_CATEGORY = Category.TOPS;
  private static final Integer PRODUCT2_PRICE = 2000;
  
  @InjectMocks
  private ProductController productController;

  @Mock
  private ProductSearchService productSearchService;

  private MockMvc mockMvc;

  @BeforeEach
  public void before() {
    mockMvc = MockMvcBuilders
        .standaloneSetup(productController)
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();
  }

  @Test
  @DisplayName("최저가격 세트 조회 성공")
  public void getLowestPricedSetSuccess() throws Exception {

    when(productSearchService.getLowestPricedSet())
        .thenReturn(new ProductSet(List.of(
            Product.builder()
                .id(PRODUCT_ID)
                .brand(Brand.builder()
                    .id(BRAND_ID)
                    .name(BRAND_NAME)
                    .build())
                .category(PRODUCT_CATEGORY)
                .price(PRODUCT_PRICE)
                .build())));

    mockMvc.perform(get("/api/v1/products/lowest-set"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.lowestPrice.products", hasSize(1)))
        .andExpect(jsonPath("$.data.lowestPrice.totalPrice", equalTo(PRODUCT_PRICE)))
        .andExpect(jsonPath("$.data.lowestPrice.products.[0].category", equalTo(PRODUCT_CATEGORY.toString())))
        .andExpect(jsonPath("$.data.lowestPrice.products.[0].brandName", equalTo(BRAND_NAME)))
        .andExpect(jsonPath("$.data.lowestPrice.products.[0].price", equalTo(PRODUCT_PRICE)))
        .andReturn();
  }

  @Test
  @DisplayName("최저가격 세트 조회 실패 - Service 에러")
  public void getLowestPricedSetFailServiceError() throws Exception {

    when(productSearchService.getLowestPricedSet())
        .thenThrow(new InternalException());

    mockMvc.perform(get("/api/v1/products/lowest-set"))
        .andDo(print())
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INTERNAL_ERROR.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("단일 브랜드 최저가격 세트 조회 성공")
  public void getLowestPricedSetForSingleBrandSuccess() throws Exception {

    when(productSearchService.getLowestPricedSetForSingleBrand())
        .thenReturn(new ProductSet(List.of(
            Product.builder()
                .id(PRODUCT_ID)
                .brand(Brand.builder()
                    .id(BRAND_ID)
                    .name(BRAND_NAME)
                    .build())
                .category(PRODUCT_CATEGORY)
                .price(PRODUCT_PRICE)
                .build())));

    mockMvc.perform(get("/api/v1/products/lowest-set")
        .param("singleBrand", "true"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.lowestPrice.brandName", equalTo(BRAND_NAME)))
        .andExpect(jsonPath("$.data.lowestPrice.products", hasSize(1)))
        .andExpect(jsonPath("$.data.lowestPrice.totalPrice", equalTo(PRODUCT_PRICE)))
        .andExpect(jsonPath("$.data.lowestPrice.products.[0].category", equalTo(PRODUCT_CATEGORY.toString())))
        .andExpect(jsonPath("$.data.lowestPrice.products.[0].brandName", equalTo(BRAND_NAME)))
        .andExpect(jsonPath("$.data.lowestPrice.products.[0].price", equalTo(PRODUCT_PRICE)))
        .andReturn();
  }

  @Test
  @DisplayName("단일 브랜드 최저가격 세트 조회 실패 - Service 에러")
  public void getLowestPricedSetForSingleBrandFailServiceError() throws Exception {

    when(productSearchService.getLowestPricedSetForSingleBrand())
        .thenThrow(new InternalException());

    mockMvc.perform(get("/api/v1/products/lowest-set")
        .param("singleBrand", "true"))
        .andDo(print())
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INTERNAL_ERROR.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("카테코리 최저/최고가격 조회 성공")
  public void getLowestHighestPricedCategorySuccess() throws Exception {

    when(productSearchService.getLowestPricedBy(any(Category.class)))
        .thenReturn(CompletableFuture.completedFuture(Optional.of(Product.builder()
            .id(PRODUCT_ID)
            .brand(Brand.builder()
                .id(BRAND_ID)
                .name(BRAND_NAME)
                .build())
            .category(PRODUCT_CATEGORY)
            .price(PRODUCT_PRICE)
            .build())));
    when(productSearchService.getHighestPricedBy(any(Category.class)))
        .thenReturn(CompletableFuture.completedFuture(Optional.of(Product.builder()
            .id(PRODUCT2_ID)
            .brand(Brand.builder()
                .id(BRAND2_ID)
                .name(BRAND2_NAME)
                .build())
            .category(PRODUCT2_CATEGORY)
            .price(PRODUCT2_PRICE)
            .build())));

    mockMvc.perform(get("/api/v1/products/lowest-highest-category")
        .param("category", PRODUCT_CATEGORY.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.category", equalTo(PRODUCT_CATEGORY.toString())))
        .andExpect(jsonPath("$.data.lowestPrice.products", hasSize(1)))
        .andExpect(jsonPath("$.data.lowestPrice.products.[0].category", equalTo(PRODUCT_CATEGORY.toString())))
        .andExpect(jsonPath("$.data.lowestPrice.products.[0].brandName", equalTo(BRAND_NAME)))
        .andExpect(jsonPath("$.data.lowestPrice.products.[0].price", equalTo(PRODUCT_PRICE)))
        .andExpect(jsonPath("$.data.highestPrice.products", hasSize(1)))
        .andExpect(jsonPath("$.data.highestPrice.products.[0].category", equalTo(PRODUCT2_CATEGORY.toString())))
        .andExpect(jsonPath("$.data.highestPrice.products.[0].brandName", equalTo(BRAND2_NAME)))
        .andExpect(jsonPath("$.data.highestPrice.products.[0].price", equalTo(PRODUCT2_PRICE)))
        .andReturn();
  }

  @Test
  @DisplayName("카테코리 최저/최고가격 실패 - 필수 param 누락")
  public void getLowestHighestPricedCategoryFailMissingParam() throws Exception {

    mockMvc.perform(get("/api/v1/products/lowest-highest-category"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("카테코리 최저/최고가격 실패 - Service 에러")
  public void getLowestHighestPricedCategoryFailServiceError() throws Exception {

    when(productSearchService.getLowestPricedBy(any(Category.class)))
        .thenReturn(CompletableFuture.failedFuture(new InternalException()));
    when(productSearchService.getHighestPricedBy(any(Category.class)))
        .thenReturn(CompletableFuture.failedFuture(new InternalException()));

    mockMvc.perform(get("/api/v1/products/lowest-highest-category")
        .param("category", PRODUCT_CATEGORY.toString()))
        .andDo(print())
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INTERNAL_ERROR.getCode())))
        .andReturn();
  }

}
