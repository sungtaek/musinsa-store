package com.musinsa.store.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.musinsa.store.common.exception.InternalException;
import com.musinsa.store.product.exception.InvalidBrandException;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {

  @InjectMocks
  private BrandService brandService;

  @Mock
  private BrandRepository brandRepository;

  private static final Long BRAND_ID = 1L;
  private static final Brand BRAND_FOR_CREATE = Brand.builder()
        .name("test")
        .productSet(new ProductSet(List.of(
            Product.builder().category(Category.TOPS).price(1000).build(),
            Product.builder().category(Category.OUTER).price(1000).build(),
            Product.builder().category(Category.PANTS).price(1000).build(),
            Product.builder().category(Category.SNEAKERS).price(1000).build(),
            Product.builder().category(Category.BAGS).price(1000).build(),
            Product.builder().category(Category.HATS).price(1000).build(),
            Product.builder().category(Category.SOCKS).price(1000).build(),
            Product.builder().category(Category.ACCESSORIES).price(1000).build())))
        .build();
  private static final Brand BRAND_FOR_UPDATE = Brand.builder()
      .id(BRAND_ID)
      .name("test")
      .productSet(new ProductSet(List.of(
          Product.builder().category(Category.TOPS).price(1000).build(),
          Product.builder().category(Category.OUTER).price(1000).build(),
          Product.builder().category(Category.PANTS).price(1000).build(),
          Product.builder().category(Category.SNEAKERS).price(1000).build(),
          Product.builder().category(Category.BAGS).price(1000).build(),
          Product.builder().category(Category.HATS).price(1000).build(),
          Product.builder().category(Category.SOCKS).price(1000).build(),
          Product.builder().category(Category.ACCESSORIES).price(1000).build())))
      .build();

  @Test
  @DisplayName("브랜드 생성 성공")
  public void createSuccess() {
    
    when(brandRepository.save(any(Brand.class)))
        .thenReturn(BRAND_FOR_CREATE.toBuilder().id(BRAND_ID).build());
    
    Brand brand = brandService.create(BRAND_FOR_CREATE);

    assertNotNull(brand);
    assertEquals(BRAND_ID, brand.getId());
  }

  @Test
  @DisplayName("브랜드 생성 실패 - db 에러")
  public void createFailDbError() {
    
    when(brandRepository.save(any(Brand.class)))
        .thenThrow(new DatabaseException());

    assertThrows(InternalException.class, () -> {
      brandService.create(BRAND_FOR_CREATE);
    });
  }

  @Test
  @DisplayName("브랜드 조회 성공")
  public void getSuccess() {
    
    when(brandRepository.get(anyLong()))
        .thenReturn(Optional.of(BRAND_FOR_UPDATE));
    
    Optional<Brand> brand = brandService.get(BRAND_ID);

    assertNotNull(brand);
    assertTrue(brand.isPresent());
    assertEquals(BRAND_FOR_UPDATE, brand.get());
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
    
    when(brandRepository.isExist(anyLong()))
        .thenReturn(true);
    when(brandRepository.save(any(Brand.class)))
        .thenReturn(BRAND_FOR_UPDATE);
    
    Brand brand = brandService.update(BRAND_FOR_UPDATE);

    assertNotNull(brand);
    assertEquals(BRAND_FOR_UPDATE, brand);
  }

  @Test
  @DisplayName("브랜드 업데이트 실패 - Not found")
  public void updateFailNotFound() {
    
    when(brandRepository.isExist(anyLong()))
        .thenReturn(false);

    assertThrows(InvalidBrandException.class, () -> {
      brandService.update(BRAND_FOR_UPDATE);
    });
  }

  @Test
  @DisplayName("브랜드 업데이트 실패 - db 에러")
  public void updateFailDbError() {
    
    when(brandRepository.isExist(anyLong()))
        .thenReturn(true);
    when(brandRepository.save(any(Brand.class)))
        .thenThrow(new DatabaseException());

    assertThrows(DatabaseException.class, () -> {
      brandService.update(BRAND_FOR_UPDATE);
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
