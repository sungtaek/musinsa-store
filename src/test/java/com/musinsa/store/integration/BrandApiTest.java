package com.musinsa.store.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.musinsa.store.common.exception.ResultCode;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class BrandApiTest {

  @Autowired
  private MockMvc mockMvc;
  
  @Test
  @DisplayName("Brand 생성 성공")
  public void createSuccess() throws Exception {

    mockMvc.perform(post("/api/v1/brands")
        .content("""
          {
            "name": "Z",
            "products": [
              { "category": "TOPS", "price": 6000 },
              { "category": "OUTER", "price": 8000 },
              { "category": "PANTS", "price": 7000 },
              { "category": "SNEAKERS", "price": 3000 },
              { "category": "BAGS", "price": 3000 },
              { "category": "HATS", "price": 1400 },
              { "category": "SOCKS", "price": 800 },
              { "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.id").exists())
        .andExpect(jsonPath("$.data.name", equalTo("Z")))
        .andExpect(jsonPath("$.data.products", hasSize(8)))
        .andExpect(jsonPath("$.data.products.[0].id").exists())
        .andExpect(jsonPath("$.data.products.[1].id").exists())
        .andExpect(jsonPath("$.data.products.[2].id").exists())
        .andExpect(jsonPath("$.data.products.[3].id").exists())
        .andExpect(jsonPath("$.data.products.[4].id").exists())
        .andExpect(jsonPath("$.data.products.[5].id").exists())
        .andExpect(jsonPath("$.data.products.[6].id").exists())
        .andExpect(jsonPath("$.data.products.[7].id").exists())
        .andReturn();
  }

  @Test
  @DisplayName("Brand 생성 실패 - 잘못된 필드")
  public void createFailInvalidField() throws Exception {

    // invalid name
    mockMvc.perform(post("/api/v1/brands")
        .content("""
          {
            "name": "",
            "products": [
              { "category": "TOPS", "price": 6000 },
              { "category": "OUTER", "price": 8000 },
              { "category": "PANTS", "price": 7000 },
              { "category": "SNEAKERS", "price": 3000 },
              { "category": "BAGS", "price": 3000 },
              { "category": "HATS", "price": 1400 },
              { "category": "SOCKS", "price": 800 },
              { "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();

    // missing product
    mockMvc.perform(post("/api/v1/brands")
        .content("""
          {
            "name": "Z"
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
    
    // invalid product (invalid price)
    mockMvc.perform(post("/api/v1/brands")
        .content("""
          {
            "name": "Z",
            "products": [
              { "category": "TOPS", "price": -1 },
              { "category": "OUTER", "price": 8000 },
              { "category": "PANTS", "price": 7000 },
              { "category": "SNEAKERS", "price": 3000 },
              { "category": "BAGS", "price": 3000 },
              { "category": "HATS", "price": 1400 },
              { "category": "SOCKS", "price": 800 },
              { "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();

    // invalid product (leak category)
    mockMvc.perform(post("/api/v1/brands")
        .content("""
          {
            "name": "Z",
            "products": [
              { "category": "OUTER", "price": 8000 },
              { "category": "PANTS", "price": 7000 },
              { "category": "SNEAKERS", "price": 3000 },
              { "category": "BAGS", "price": 3000 },
              { "category": "HATS", "price": 1400 },
              { "category": "SOCKS", "price": 800 },
              { "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();

    // invalid product (duplicated category)
    mockMvc.perform(post("/api/v1/brands")
        .content("""
          {
            "name": "Z",
            "products": [
              { "category": "TOPS", "price": 6000 },
              { "category": "TOPS", "price": 6000 },
              { "category": "OUTER", "price": 8000 },
              { "category": "PANTS", "price": 7000 },
              { "category": "SNEAKERS", "price": 3000 },
              { "category": "BAGS", "price": 3000 },
              { "category": "HATS", "price": 1400 },
              { "category": "SOCKS", "price": 800 },
              { "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 조회 성공")
  public void getSuccess() throws Exception {

    mockMvc.perform(get("/api/v1/brands/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.id").exists())
        .andExpect(jsonPath("$.data.name", equalTo("A")))
        .andExpect(jsonPath("$.data.products", hasSize(8)))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 조회 실패 - Not found")
  public void getFailNotFound() throws Exception {

    mockMvc.perform(get("/api/v1/brands/100"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.NOT_FOUND.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 업데이트 성공")
  public void updateSuccess() throws Exception {

    mockMvc.perform(post("/api/v1/brands/1")
        .content("""
          {
            "name": "AA",
            "products": [
              { "id": 1, "category": "TOPS", "price": 6000 },
              { "id": 2, "category": "OUTER", "price": 8000 },
              { "id": 3, "category": "PANTS", "price": 7000 },
              { "id": 4, "category": "SNEAKERS", "price": 3000 },
              { "id": 5, "category": "BAGS", "price": 3000 },
              { "id": 6, "category": "HATS", "price": 1400 },
              { "id": 7, "category": "SOCKS", "price": 800 },
              { "id": 8, "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andExpect(jsonPath("$.data.id").exists())
        .andExpect(jsonPath("$.data.name", equalTo("AA")))
        .andExpect(jsonPath("$.data.products", hasSize(8)))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 업데이트 실패 - 잘못된 필드")
  public void updateFailInvalidField() throws Exception {

    // invalid name
    mockMvc.perform(post("/api/v1/brands/1")
        .content("""
          {
            "name": "",
            "products": [
              { "id": 1, "category": "TOPS", "price": 6000 },
              { "id": 2, "category": "OUTER", "price": 8000 },
              { "id": 3, "category": "PANTS", "price": 7000 },
              { "id": 4, "category": "SNEAKERS", "price": 3000 },
              { "id": 5, "category": "BAGS", "price": 3000 },
              { "id": 6, "category": "HATS", "price": 1400 },
              { "id": 7, "category": "SOCKS", "price": 800 },
              { "id": 8, "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();

    // missing product
    mockMvc.perform(post("/api/v1/brands/1")
        .content("""
          {
            "name": "AA"
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
    
    // invalid product (missing id)
    mockMvc.perform(post("/api/v1/brands/1")
        .content("""
          {
            "name": "AA",
            "products": [
              { "category": "TOPS", "price": 6000 },
              { "id": 2, "category": "OUTER", "price": 8000 },
              { "id": 3, "category": "PANTS", "price": 7000 },
              { "id": 4, "category": "SNEAKERS", "price": 3000 },
              { "id": 5, "category": "BAGS", "price": 3000 },
              { "id": 6, "category": "HATS", "price": 1400 },
              { "id": 7, "category": "SOCKS", "price": 800 },
              { "id": 8, "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();

    // invalid product (invalid price)
    mockMvc.perform(post("/api/v1/brands/1")
        .content("""
          {
            "name": "AA",
            "products": [
              { "id": 1, "category": "TOPS", "price": -1 },
              { "id": 2, "category": "OUTER", "price": 8000 },
              { "id": 3, "category": "PANTS", "price": 7000 },
              { "id": 4, "category": "SNEAKERS", "price": 3000 },
              { "id": 5, "category": "BAGS", "price": 3000 },
              { "id": 6, "category": "HATS", "price": 1400 },
              { "id": 7, "category": "SOCKS", "price": 800 },
              { "id": 8, "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();

    // invalid product (leak category)
    mockMvc.perform(post("/api/v1/brands/1")
        .content("""
          {
            "name": "AA",
            "products": [
              { "id": 2, "category": "OUTER", "price": 8000 },
              { "id": 3, "category": "PANTS", "price": 7000 },
              { "id": 4, "category": "SNEAKERS", "price": 3000 },
              { "id": 5, "category": "BAGS", "price": 3000 },
              { "id": 6, "category": "HATS", "price": 1400 },
              { "id": 7, "category": "SOCKS", "price": 800 },
              { "id": 8, "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();

    // invalid product (duplicated category)
    mockMvc.perform(post("/api/v1/brands/1")
        .content("""
          {
            "name": "AA",
            "products": [
              { "id": 1, "category": "TOPS", "price": 6000 },
              { "id": 2, "category": "OUTER", "price": 8000 },
              { "id": 3, "category": "PANTS", "price": 7000 },
              { "id": 4, "category": "SNEAKERS", "price": 3000 },
              { "id": 5, "category": "BAGS", "price": 3000 },
              { "id": 6, "category": "HATS", "price": 1400 },
              { "id": 7, "category": "SOCKS", "price": 800 },
              { "id": 8, "category": "ACCESSORIES", "price": 1000 },
              { "id": 9, "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
    
    // invalid product (different from current product)
    mockMvc.perform(post("/api/v1/brands/1")
        .content("""
          {
            "name": "AA",
            "products": [
              { "id": 1, "category": "TOPS", "price": 6000 },
              { "id": 2, "category": "OUTER", "price": 8000 },
              { "id": 3, "category": "PANTS", "price": 7000 },
              { "id": 4, "category": "SNEAKERS", "price": 3000 },
              { "id": 5, "category": "BAGS", "price": 3000 },
              { "id": 6, "category": "HATS", "price": 1400 },
              { "id": 7, "category": "SOCKS", "price": 800 },
              { "id": 10, "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.INVALID_PARAMETER.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 업데이트 실패 - Not found")
  public void updateFailNotFound() throws Exception {

    mockMvc.perform(post("/api/v1/brands/100")
        .content("""
          {
            "name": "AA",
            "products": [
              { "id": 1, "category": "TOPS", "price": 6000 },
              { "id": 2, "category": "OUTER", "price": 8000 },
              { "id": 3, "category": "PANTS", "price": 7000 },
              { "id": 4, "category": "SNEAKERS", "price": 3000 },
              { "id": 5, "category": "BAGS", "price": 3000 },
              { "id": 6, "category": "HATS", "price": 1400 },
              { "id": 7, "category": "SOCKS", "price": 800 },
              { "id": 8, "category": "ACCESSORIES", "price": 1000 }
            ]
          }
            """)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.NOT_FOUND.getCode())))
        .andReturn();
  }

  @Test
  @DisplayName("Brand 삭제 성공")
  public void deleteSuccess() throws Exception {

    // delete exists
    mockMvc.perform(delete("/api/v1/brands/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andReturn();
    
    // check
    mockMvc.perform(get("/api/v1/brands/1"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.NOT_FOUND.getCode())))
        .andReturn();
    
    // delete not exists
    mockMvc.perform(delete("/api/v1/brands/100"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code", equalTo(ResultCode.SUCCESS.getCode())))
        .andReturn();
  }

}
