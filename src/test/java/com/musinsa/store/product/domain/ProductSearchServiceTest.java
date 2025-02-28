package com.musinsa.store.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.musinsa.store.common.exception.DatabaseException;

@ExtendWith(MockitoExtension.class)
public class ProductSearchServiceTest {

  @InjectMocks
  private ProductSearchService productSearchService;

  @Mock
  private ProductRepository productRepository;

  private static final Brand BRAND_A = Brand.builder().name("A").build();
  private static final Brand BRAND_B = Brand.builder().name("B").build();

  @Test
  @DisplayName("최저가격 세트 검색 성공")
  public void getLowestPricedSetSuccess() {
    List<Product> lowestProducts = List.of(
      Product.builder().brand(BRAND_A).category(Category.TOPS).price(1000).build(),
      Product.builder().brand(BRAND_B).category(Category.OUTER).price(1000).build(),
      Product.builder().brand(BRAND_A).category(Category.PANTS).price(1000).build(),
      Product.builder().brand(BRAND_B).category(Category.SNEAKERS).price(1000).build(),
      Product.builder().brand(BRAND_A).category(Category.BAGS).price(1000).build(),
      Product.builder().brand(BRAND_B).category(Category.HATS).price(1000).build(),
      Product.builder().brand(BRAND_A).category(Category.SOCKS).price(1000).build(),
      Product.builder().brand(BRAND_B).category(Category.ACCESSORIES).price(1000).build());
    
    when(productRepository.getLowestPricedSet())
        .thenReturn(lowestProducts);
    
    List<Product> products = productSearchService.getLowestPricedSet();

    assertNotNull(products);
    assertThat(products).isEqualTo(lowestProducts);
  }

  @Test
  @DisplayName("최저가격 세트 검색 실패 - DB 에러")
  public void getLowestPricedSetFailDbError() {

    when(productRepository.getLowestPricedSet())
        .thenThrow(new DatabaseException());
    
    assertThrows(DatabaseException.class, () -> {
      productRepository.getLowestPricedSet();
    });
  }

  @Test
  @DisplayName("한 브랜드 최저가격 세트 검색 성공")
  public void getLowestPricedSetForSingleBrandSuccess() {
    List<Product> lowestProducts = List.of(
      Product.builder().brand(BRAND_A).category(Category.TOPS).price(1000).build(),
      Product.builder().brand(BRAND_A).category(Category.OUTER).price(1000).build(),
      Product.builder().brand(BRAND_A).category(Category.PANTS).price(1000).build(),
      Product.builder().brand(BRAND_A).category(Category.SNEAKERS).price(1000).build(),
      Product.builder().brand(BRAND_A).category(Category.BAGS).price(1000).build(),
      Product.builder().brand(BRAND_A).category(Category.HATS).price(1000).build(),
      Product.builder().brand(BRAND_A).category(Category.SOCKS).price(1000).build(),
      Product.builder().brand(BRAND_A).category(Category.ACCESSORIES).price(1000).build());
    
    when(productRepository.getLowestPricedSetForSingleBrand())
        .thenReturn(lowestProducts);
    
    List<Product> products = productSearchService.getLowestPricedSetForSingleBrand();

    assertNotNull(products);
    assertThat(products).isEqualTo(lowestProducts);
  }

  @Test
  @DisplayName("한 브랜드 최저가격 세트 검색 실패 - DB 에러")
  public void getLowestPricedSetForSingleBrandFailDbError() {

    when(productRepository.getLowestPricedSetForSingleBrand())
        .thenThrow(new DatabaseException());
    
    assertThrows(DatabaseException.class, () -> {
      productRepository.getLowestPricedSetForSingleBrand();
    });
  }

  @Test
  @DisplayName("카테고리 최저가격 검색 성공")
  public void getLowestPricedByCateogrySuccess() throws Exception {
    Product lowestProduct = Product.builder().brand(BRAND_A).category(Category.TOPS).price(1000).build();
    
    when(productRepository.getLowestPricedBy(any(Category.class)))
        .thenReturn(Optional.of(lowestProduct));
    
    Optional<Product> product = productSearchService.getLowestPricedBy(Category.TOPS).get();

    assertNotNull(product);
    assertEquals(lowestProduct, product.get());
  }

  @Test
  @DisplayName("카테고리 최저가격 검색 실패 - DB 에러")
  public void getLowestPricedByCateogryFailDbError() throws Exception {
    
    when(productRepository.getLowestPricedBy(any(Category.class)))
        .thenThrow(new DatabaseException());
    
    ExecutionException ex = assertThrows(ExecutionException.class, () -> {
      productSearchService.getLowestPricedBy(Category.TOPS).get();
    });
    assertEquals(DatabaseException.class, ex.getCause().getClass());
  }

  @Test
  @DisplayName("카테고리 최고가격 검색 성공")
  public void getHighestPricedByCateogrySuccess() throws Exception {
    Product highestProduct = Product.builder().brand(BRAND_A).category(Category.TOPS).price(2000).build();
    
    when(productRepository.getHighestPricedBy(any(Category.class)))
        .thenReturn(Optional.of(highestProduct));
    
    Optional<Product> product = productSearchService.getHighestPricedBy(Category.TOPS).get();

    assertNotNull(product);
    assertEquals(highestProduct, product.get());
  }

  @Test
  @DisplayName("카테고리 최고가격 검색 실패 - DB 에러")
  public void getHighestPricedByCateogryFailDbError() throws Exception {
    
    when(productRepository.getHighestPricedBy(any(Category.class)))
        .thenThrow(new DatabaseException());
    
    ExecutionException ex = assertThrows(ExecutionException.class, () -> {
      productSearchService.getHighestPricedBy(Category.TOPS).get();
    });
    assertEquals(DatabaseException.class, ex.getCause().getClass());
  }

}
