package com.musinsa.store.product.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.store.common.exception.GlobalExceptionHandler;
import com.musinsa.store.common.exception.InternalException;
import com.musinsa.store.common.exception.ResultCode;
import com.musinsa.store.product.api.dto.BrandPayload;
import com.musinsa.store.product.api.dto.ProductPayload;
import com.musinsa.store.product.domain.Brand;
import com.musinsa.store.product.domain.BrandService;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.Product;

@ExtendWith(MockitoExtension.class)
public class BrandControllerTest {
  private static final ObjectMapper om = new ObjectMapper();
  private static final Long BRAND_ID = 1L;
  private static final String BRAND_NAME = "TEST_BRAND";
  private static final Long PRODUCT_ID = 10L;
  private static final Category PRODUCT_CATEGORY = Category.TOPS;
  private static final Integer PRODUCT_PRICE = 1000;

  @InjectMocks
  private BrandController brandController;

  @Mock
  private BrandService brandService;

  private MockMvc mockMvc;

  @BeforeEach
  public void before() {
    mockMvc = MockMvcBuilders
        .standaloneSetup(brandController)
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();
  }

  @Test
  @DisplayName("Brand 생성 성공")
  public void createSuccess() throws Exception {

    when(brandService.create(any(Brand.class)))
        .thenReturn(Brand.builder()
            .id(BRAND_ID)
            .name(BRAND_NAME)
            .products(List.of(Product.builder()
                .id(PRODUCT_ID)
                .category(PRODUCT_CATEGORY)
                .price(PRODUCT_PRICE)
                .build()))
            .build());

    mockMvc.perform(post("/api/v1/brands")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name(BRAND_NAME)
            .products(List.of(ProductPayload.builder()
                .category(PRODUCT_CATEGORY)
                .price(PRODUCT_PRICE)
                .build()))
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.id", equalTo(BRAND_ID.intValue())))
        .andExpect(jsonPath("$.data.name", equalTo(BRAND_NAME)))
        .andExpect(jsonPath("$.data.products", hasSize(1)))
        .andExpect(jsonPath("$.data.products[0].id", equalTo(PRODUCT_ID.intValue())))
        .andExpect(jsonPath("$.data.products[0].category", equalTo(PRODUCT_CATEGORY.toString())))
        .andExpect(jsonPath("$.data.products[0].price", equalTo(PRODUCT_PRICE)))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 생성 실패 - 잘못된 필드")
  public void createFailInvalidField() throws Exception {

    // invalid brand name
    mockMvc.perform(post("/api/v1/brands")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name("")
            .products(List.of(ProductPayload.builder()
                .category(PRODUCT_CATEGORY)
                .price(PRODUCT_PRICE)
                .build()))
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
    
    // missing product
    mockMvc.perform(post("/api/v1/brands")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name(BRAND_NAME)
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();

    // invalid product (missing category)
    mockMvc.perform(post("/api/v1/brands")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name(BRAND_NAME)
            .products(List.of(ProductPayload.builder()
                .price(PRODUCT_PRICE)
                .build()))
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
    
    // invalid product (invalid price)
    mockMvc.perform(post("/api/v1/brands")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name(BRAND_NAME)
            .products(List.of(ProductPayload.builder()
                .category(PRODUCT_CATEGORY)
                .price(-1)
                .build()))
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();

  }

  @Test
  @DisplayName("Brand 생성 실패 - Service 에러")
  public void createFailServiceError() throws Exception {

    when(brandService.create(any(Brand.class)))
        .thenThrow(new InternalException());

    mockMvc.perform(post("/api/v1/brands")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name(BRAND_NAME)
            .products(List.of(ProductPayload.builder()
                .category(PRODUCT_CATEGORY)
                .price(PRODUCT_PRICE)
                .build()))
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INTERNAL_ERROR.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 조회 성공")
  public void getSuccess() throws Exception {

    when(brandService.get(anyLong()))
        .thenReturn(Optional.of(Brand.builder()
            .id(BRAND_ID)
            .name(BRAND_NAME)
            .products(List.of(Product.builder()
                .id(PRODUCT_ID)
                .category(PRODUCT_CATEGORY)
                .price(PRODUCT_PRICE)
                .build()))
            .build()));

    mockMvc.perform(get("/api/v1/brands/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.id", equalTo(BRAND_ID.intValue())))
        .andExpect(jsonPath("$.data.name", equalTo(BRAND_NAME)))
        .andExpect(jsonPath("$.data.products", hasSize(1)))
        .andExpect(jsonPath("$.data.products[0].id", equalTo(PRODUCT_ID.intValue())))
        .andExpect(jsonPath("$.data.products[0].category", equalTo(PRODUCT_CATEGORY.toString())))
        .andExpect(jsonPath("$.data.products[0].price", equalTo(PRODUCT_PRICE)))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 조회 실패 - Not found")
  public void getFailNotFound() throws Exception {

    when(brandService.get(anyLong()))
        .thenReturn(Optional.empty());

    mockMvc.perform(get("/api/v1/brands/1"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.NOT_FOUND.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 조회 실패 - Service 에러")
  public void getFailServiceError() throws Exception {

    when(brandService.get(anyLong()))
        .thenThrow(new InternalException());

    mockMvc.perform(get("/api/v1/brands/1"))
        .andDo(print())
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INTERNAL_ERROR.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 업데이트 성공")
  public void updateSuccess() throws Exception {

    when(brandService.update(any(Brand.class)))
        .thenReturn(Optional.of(Brand.builder()
            .id(BRAND_ID)
            .name(BRAND_NAME)
            .products(List.of(Product.builder()
                .id(PRODUCT_ID)
                .category(PRODUCT_CATEGORY)
                .price(PRODUCT_PRICE)
                .build()))
            .build()));

    mockMvc.perform(post("/api/v1/brands/1")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name(BRAND_NAME)
            .products(List.of(ProductPayload.builder()
                .id(PRODUCT_ID)
                .category(PRODUCT_CATEGORY)
                .price(PRODUCT_PRICE)
                .build()))
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.id", equalTo(BRAND_ID.intValue())))
        .andExpect(jsonPath("$.data.name", equalTo(BRAND_NAME)))
        .andExpect(jsonPath("$.data.products", hasSize(1)))
        .andExpect(jsonPath("$.data.products[0].id", equalTo(PRODUCT_ID.intValue())))
        .andExpect(jsonPath("$.data.products[0].category", equalTo(PRODUCT_CATEGORY.toString())))
        .andExpect(jsonPath("$.data.products[0].price", equalTo(PRODUCT_PRICE)))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 업데이트 실패 - 잘못된 필드")
  public void updateFailInvalidName() throws Exception {

    // invalid brand name
    mockMvc.perform(post("/api/v1/brands/1")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name("")
            .products(List.of(ProductPayload.builder()
                .id(PRODUCT_ID)
                .category(PRODUCT_CATEGORY)
                .price(PRODUCT_PRICE)
                .build()))
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
    
    // missing product
    mockMvc.perform(post("/api/v1/brands/1")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name(BRAND_NAME)
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
    
    // invalid product (missing category)
    mockMvc.perform(post("/api/v1/brands/1")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name(BRAND_NAME)
            .products(List.of(ProductPayload.builder()
                .id(PRODUCT_ID)
                .price(PRODUCT_PRICE)
                .build()))
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
    
    // invalid product (invalid price)
    mockMvc.perform(post("/api/v1/brands/1")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name(BRAND_NAME)
            .products(List.of(ProductPayload.builder()
                .id(PRODUCT_ID)
                .category(PRODUCT_CATEGORY)
                .price(-1)
                .build()))
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();

  }

  @Test
  @DisplayName("Brand 업데이트 실패 - Not found")
  public void updateFailNotFound() throws Exception {

    when(brandService.update(any(Brand.class)))
        .thenReturn(Optional.empty());

    mockMvc.perform(post("/api/v1/brands/1")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name(BRAND_NAME)
            .products(List.of(ProductPayload.builder()
                .id(PRODUCT_ID)
                .category(PRODUCT_CATEGORY)
                .price(PRODUCT_PRICE)
                .build()))
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.NOT_FOUND.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 업데이트 실패 - Service 에러")
  public void updateFailServiceError() throws Exception {

    when(brandService.update(any(Brand.class)))
        .thenThrow(new InternalException());

    mockMvc.perform(post("/api/v1/brands/1")
        .content(om.writeValueAsString(BrandPayload.builder()
            .name(BRAND_NAME)
            .products(List.of(ProductPayload.builder()
                .id(PRODUCT_ID)
                .category(PRODUCT_CATEGORY)
                .price(PRODUCT_PRICE)
                .build()))
            .build()))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INTERNAL_ERROR.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 삭제 성공")
  public void deleteSuccess() throws Exception {

    mockMvc.perform(delete("/api/v1/brands/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 삭제 실패 - Service 에러")
  public void deleteFailServiceError() throws Exception {

    doThrow(new InternalException())
        .when(brandService).delete(anyLong());

    mockMvc.perform(delete("/api/v1/brands/1"))
        .andDo(print())
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INTERNAL_ERROR.getCode())))
        .andReturn();
  }

}
