package com.musinsa.store.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.musinsa.store.common.exception.DatabaseException;
import com.musinsa.store.product.exception.InvalidBrandException;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

  @InjectMocks
  private BrandService brandService;

  @Mock
  private BrandRepository brandRepository;

  private static final Long BRAND_ID = 1L;
  private static final String BRAND_NAME = "BRAND_A";

  private static final List<Product> PRODUCTS = List.of(
      Product.builder().id(1L).category(Category.TOPS).price(1000).build(),
      Product.builder().id(2L).category(Category.OUTER).price(1000).build(),
      Product.builder().id(3L).category(Category.PANTS).price(1000).build(),
      Product.builder().id(4L).category(Category.SNEAKERS).price(1000).build(),
      Product.builder().id(5L).category(Category.BAGS).price(1000).build(),
      Product.builder().id(6L).category(Category.HATS).price(1000).build(),
      Product.builder().id(7L).category(Category.SOCKS).price(1000).build(),
      Product.builder().id(8L).category(Category.ACCESSORIES).price(1000).build());

  private static final List<Product> MISSED_PRODUCTS = List.of(
      Product.builder().id(1L).category(Category.TOPS).price(1000).build(),
      Product.builder().id(2L).category(Category.OUTER).price(1000).build(),
      Product.builder().id(3L).category(Category.PANTS).price(1000).build());

  private static final List<Product> DUPLICATED_PRODUCTS = List.of(
      Product.builder().id(1L).category(Category.TOPS).price(1000).build(),
      Product.builder().id(2L).category(Category.OUTER).price(1000).build(),
      Product.builder().id(3L).category(Category.PANTS).price(1000).build(),
      Product.builder().id(4L).category(Category.SNEAKERS).price(1000).build(),
      Product.builder().id(5L).category(Category.BAGS).price(1000).build(),
      Product.builder().id(6L).category(Category.HATS).price(1000).build(),
      Product.builder().id(7L).category(Category.SOCKS).price(1000).build(),
      Product.builder().id(8L).category(Category.SOCKS).price(1000).build(),
      Product.builder().id(9L).category(Category.ACCESSORIES).price(1000).build());

  @Test
  @DisplayName("브랜드 생성 성공")
  public void createSuccess() {
    Brand brand = Brand.builder()
        .name(BRAND_NAME)
        .products(PRODUCTS)
        .build();
    
    when(brandRepository.save(any(Brand.class)))
        .thenReturn(Brand.builder().id(BRAND_ID).name(BRAND_NAME).products(PRODUCTS).build());

    brand = brandService.create(brand);

    assertNotNull(brand);
    assertEquals(BRAND_ID, brand.getId());
  }

  @Test
  @DisplayName("브랜드 생성 실패 - 카테고리 부족")
  public void createFailMissingCategory() {
    Brand brand = Brand.builder()
        .name(BRAND_NAME)
        .products(MISSED_PRODUCTS)
        .build();
    
    assertThrows(InvalidBrandException.class, () -> {
      brandService.create(brand);
    });
  }

  @Test
  @DisplayName("브랜드 생성 실패 - 중복 카테고리")
  public void createFailDuplicatedCategory() {
    Brand brand = Brand.builder()
        .name(BRAND_NAME)
        .products(DUPLICATED_PRODUCTS)
        .build();
    
    assertThrows(InvalidBrandException.class, () -> {
      brandService.create(brand);
    });
  }

  @Test
  @DisplayName("브랜드 생성 실패 - db 에러")
  public void createFailDbError() {
    Brand brand = Brand.builder()
        .name(BRAND_NAME)
        .products(PRODUCTS)
        .build();
    
    when(brandRepository.save(any(Brand.class)))
        .thenThrow(new DatabaseException());

    assertThrows(DatabaseException.class, () -> {
      brandService.create(brand);
    });
  }

  @Test
  @DisplayName("브랜드 조회 성공")
  public void getSuccess() {
    Brand brand = Brand.builder()
        .id(BRAND_ID)
        .name(BRAND_NAME)
        .products(PRODUCTS)
        .build();
    
    when(brandRepository.get(anyLong()))
        .thenReturn(Optional.of(brand));
    
    Brand foundBrand = brandService.get(BRAND_ID).get();

    assertNotNull(foundBrand);
    assertEquals(brand, foundBrand);
  }

  @Test
  @DisplayName("브랜드 조회 실패 - db 에러")
  public void getFailDbError() {
    
    when(brandRepository.get(anyLong()))
        .thenThrow(new DatabaseException());

    assertThrows(DatabaseException.class, () -> {
      brandService.get(BRAND_ID);
    });
  }

  @Test
  @DisplayName("브랜드 업데이트 성공")
  public void updateSuccess() {
    Brand brand = Brand.builder()
        .id(BRAND_ID)
        .name(BRAND_NAME)
        .products(PRODUCTS)
        .build();
    
    when(brandRepository.get(anyLong()))
        .thenReturn(Optional.of(brand));
    when(brandRepository.save(any(Brand.class)))
        .thenReturn(brand);
    
    Brand updatedBrand = brandService.update(brand).get();

    assertNotNull(updatedBrand);
    assertEquals(brand, updatedBrand);
  }

  @Test
  @DisplayName("브랜드 업데이트 실패 - 카테고리 부족")
  public void updateFailMissingCategory() {
    Brand brand = Brand.builder()
        .name(BRAND_NAME)
        .products(MISSED_PRODUCTS)
        .build();

    assertThrows(InvalidBrandException.class, () -> {
      brandService.create(brand);
    });
  }

  @Test
  @DisplayName("브랜드 업데이트 실패 - 중복 카테고리")
  public void updateFailDuplicatedCategory() {
    Brand brand = Brand.builder()
        .name(BRAND_NAME)
        .products(DUPLICATED_PRODUCTS)
        .build();
    
    assertThrows(InvalidBrandException.class, () -> {
      brandService.create(brand);
    });
  }

  @Test
  @DisplayName("브랜드 업데이트 실패 - Not found")
  public void updateFailNotFound() {
    Brand brand = Brand.builder()
        .id(BRAND_ID)
        .name(BRAND_NAME)
        .products(PRODUCTS)
        .build();
    
    when(brandRepository.get(anyLong()))
        .thenReturn(Optional.empty());

    Optional<Brand> updatedBrand = brandService.update(brand);

    assertFalse(updatedBrand.isPresent());
  }

  @Test
  @DisplayName("브랜드 업데이트 실패 - db 에러")
  public void updateFailDbError() {
    Brand brand = Brand.builder()
        .id(BRAND_ID)
        .name(BRAND_NAME)
        .products(PRODUCTS)
        .build();
    
    when(brandRepository.get(anyLong()))
        .thenReturn(Optional.of(brand));
    when(brandRepository.save(any(Brand.class)))
        .thenThrow(new DatabaseException());

    assertThrows(DatabaseException.class, () -> {
      brandService.update(brand);
    });
  }

  @Test
  @DisplayName("브랜드 삭제 성공")
  public void deleteSuccess() {
    
    brandService.delete(BRAND_ID);
  }

  @Test
  @DisplayName("브랜드 삭제 실패 - db 에러")
  public void deleteFailDbError() {
    
    doThrow(new DatabaseException())
        .when(brandRepository).delete(anyLong());

    assertThrows(DatabaseException.class, () -> {
      brandService.delete(BRAND_ID);
    });
  }

}
