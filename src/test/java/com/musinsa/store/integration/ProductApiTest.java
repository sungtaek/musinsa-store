package com.musinsa.store.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.musinsa.store.common.exception.ResultCode;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ProductApiTest {
  
  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("최저가격 세트 조회 성공")
  public void getLowestPricedSetSuccess() throws Exception {

    mockMvc.perform(get("/api/v1/products/lowest-set"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.lowestPrice.products", hasSize(8)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'TOPS')].brandName", contains("C")))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'TOPS')].price", contains(10000)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'OUTER')].brandName", contains("E")))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'OUTER')].price", contains(5000)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'PANTS')].brandName", contains("D")))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'PANTS')].price", contains(3000)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'SNEAKERS')].brandName", contains("G")))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'SNEAKERS')].price", contains(9000)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'BAGS')].brandName", contains("A")))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'BAGS')].price", contains(2000)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'HATS')].brandName", contains("D")))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'HATS')].price", contains(1500)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'SOCKS')].brandName", contains("I")))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'SOCKS')].price", contains(1700)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'ACCESSORIES')].brandName", contains("F")))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'ACCESSORIES')].price", contains(1900)))
        .andExpect(jsonPath("$.data.lowestPrice.totalPrice", equalTo(34100)))
        .andReturn();
  }

  @Test
  @DisplayName("단일 브랜드 최저가격 세트 조회 성공")
  public void getLowestPricedSetForSingleBrandSuccess() throws Exception {

    mockMvc.perform(get("/api/v1/products/lowest-set")
        .queryParam("singleBrand", "true"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.lowestPrice.brandName", equalTo("D")))
        .andExpect(jsonPath("$.data.lowestPrice.products", hasSize(8)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'TOPS')].price", contains(10100)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'OUTER')].price", contains(5100)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'PANTS')].price", contains(3000)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'SNEAKERS')].price", contains(9500)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'BAGS')].price", contains(2500)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'HATS')].price", contains(1500)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'SOCKS')].price", contains(2400)))
        .andExpect(jsonPath("$.data.lowestPrice.products[?(@.category == 'ACCESSORIES')].price", contains(2000)))
        .andExpect(jsonPath("$.data.lowestPrice.totalPrice", equalTo(36100)))
        .andReturn();
  }

  @Test
  @DisplayName("카테코리 최저/최고가격 조회 성공")
  public void getLowestHighestPricedCategorySuccess() throws Exception {

    mockMvc.perform(get("/api/v1/products/lowest-highest-category")
        .queryParam("category", "TOPS"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.category", equalTo("TOPS")))
        .andExpect(jsonPath("$.data.lowestPrice.products", hasSize(1)))
        .andExpect(jsonPath("$.data.lowestPrice.products[0].brandName", equalTo("C")))
        .andExpect(jsonPath("$.data.lowestPrice.products[0].price", equalTo(10000)))
        .andExpect(jsonPath("$.data.highestPrice.products", hasSize(1)))
        .andExpect(jsonPath("$.data.highestPrice.products[0].brandName", equalTo("I")))
        .andExpect(jsonPath("$.data.highestPrice.products[0].price", equalTo(11400)))
        .andReturn();

    mockMvc.perform(get("/api/v1/products/lowest-highest-category")
        .queryParam("category", "SOCKS"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.category", equalTo("SOCKS")))
        .andExpect(jsonPath("$.data.lowestPrice.products", hasSize(1)))
        .andExpect(jsonPath("$.data.lowestPrice.products[0].brandName", equalTo("I")))
        .andExpect(jsonPath("$.data.lowestPrice.products[0].price", equalTo(1700)))
        .andExpect(jsonPath("$.data.highestPrice.products", hasSize(1)))
        .andExpect(jsonPath("$.data.highestPrice.products[0].brandName", equalTo("D")))
        .andExpect(jsonPath("$.data.highestPrice.products[0].price", equalTo(2400)))
        .andReturn();
  }

  @Test
  @DisplayName("카테코리 최저/최고가격 조회 실패 - 잘못된 카테고리 parameter")
  public void getLowestHighestPricedCategoryFailMissingParam() throws Exception {

    // missing category
    mockMvc.perform(get("/api/v1/products/lowest-highest-category"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
    
    // invalid category
    mockMvc.perform(get("/api/v1/products/lowest-highest-category")
        .queryParam("category", "GLASSES"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
  }

}
