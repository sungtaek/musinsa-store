package com.musinsa.store.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.musinsa.store.common.cache.CacheStorage;
import com.musinsa.store.common.event.BrandEvent;
import com.musinsa.store.common.exception.DatabaseException;
import com.musinsa.store.product.domain.ProductSearchService.CacheProperties;
import com.musinsa.store.product.domain.dto.ProductDto;
import com.musinsa.store.product.domain.dto.ProductSet;
import com.musinsa.store.product.domain.dto.SearchOrder;

@ExtendWith(MockitoExtension.class)
public class ProductSearchServiceTest {

  @InjectMocks
  private ProductSearchService productSearchService;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private CacheStorage cacheStorage;

  @Spy
  private CacheProperties cacheProperties = CacheProperties.builder()
      .active(false)
      .ttl(60)
      .build();

  private static final String BRAND_A = "AA";
  private static final String BRAND_B = "BB";

  @Test
  @DisplayName("최저가격 세트 검색 성공")
  public void getLowestPricedSetSuccess() {
    ProductSet lowestProducts = ProductSet.of(
      ProductDto.builder().brandName(BRAND_A).category(Category.TOPS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_B).category(Category.OUTER).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.PANTS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_B).category(Category.SNEAKERS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.BAGS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_B).category(Category.HATS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.SOCKS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_B).category(Category.ACCESSORIES).price(1000).build());
    
    when(productRepository.findSet(eq(SearchOrder.LOWEST_PRICE)))
        .thenReturn(lowestProducts);
    
    ProductSet productSet = productSearchService.searchSet(SearchOrder.LOWEST_PRICE);

    assertNotNull(productSet);
    assertThat(productSet).containsExactlyInAnyOrderElementsOf(lowestProducts);
  }

  @Test
  @DisplayName("최저가격 세트 검색 실패 - DB 에러")
  public void getLowestPricedSetFailDbError() {

    when(productRepository.findSet(eq(SearchOrder.LOWEST_PRICE)))
        .thenThrow(new DatabaseException());
    
    assertThrows(DatabaseException.class, () -> {
      productSearchService.searchSet(SearchOrder.LOWEST_PRICE);
    });
  }

  @Test
  @DisplayName("한 브랜드 최저가격 세트 검색 성공")
  public void getLowestPricedSetForSingleBrandSuccess() {
    ProductSet lowestProducts = ProductSet.of(
      ProductDto.builder().brandName(BRAND_A).category(Category.TOPS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.OUTER).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.PANTS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.SNEAKERS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.BAGS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.HATS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.SOCKS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.ACCESSORIES).price(1000).build());
    
    when(productRepository.findSetForSingleBrand(eq(SearchOrder.LOWEST_PRICE)))
        .thenReturn(lowestProducts);
    
    ProductSet productSet = productSearchService.searchSetForSingleBrand(SearchOrder.LOWEST_PRICE);

    assertNotNull(productSet);
    assertThat(productSet).containsExactlyInAnyOrderElementsOf(lowestProducts);
  }

  @Test
  @DisplayName("한 브랜드 최저가격 세트 검색 실패 - DB 에러")
  public void getLowestPricedSetForSingleBrandFailDbError() {

    when(productRepository.findSetForSingleBrand(eq(SearchOrder.LOWEST_PRICE)))
        .thenThrow(new DatabaseException());
    
    assertThrows(DatabaseException.class, () -> {
      productSearchService.searchSetForSingleBrand(SearchOrder.LOWEST_PRICE);
    });
  }

  @Test
  @DisplayName("카테고리 최저가격 검색 성공")
  public void getLowestPricedByCateogrySuccess() throws Exception {
    ProductDto lowestProduct = ProductDto.builder().brandName(BRAND_A).category(Category.TOPS).price(1000).build();

    when(productRepository.findBy(any(Category.class), eq(SearchOrder.LOWEST_PRICE)))
        .thenReturn(Optional.of(lowestProduct));
    
    Optional<ProductDto> product = productSearchService.searchCategory(Category.TOPS, SearchOrder.LOWEST_PRICE).get();

    assertNotNull(product);
    assertEquals(lowestProduct, product.get());
  }

  @Test
  @DisplayName("카테고리 최저가격 검색 실패 - DB 에러")
  public void getLowestPricedByCateogryFailDbError() throws Exception {
    
    when(productRepository.findBy(any(Category.class), eq(SearchOrder.LOWEST_PRICE)))
        .thenThrow(new DatabaseException());
    
    ExecutionException ex = assertThrows(ExecutionException.class, () -> {
      productSearchService.searchCategory(Category.TOPS, SearchOrder.LOWEST_PRICE).get();
    });
    assertEquals(DatabaseException.class, ex.getCause().getClass());
  }

  @Test
  @DisplayName("카테고리 최고가격 검색 성공")
  public void getHighestPricedByCateogrySuccess() throws Exception {
    ProductDto highestProduct = ProductDto.builder().brandName(BRAND_A).category(Category.TOPS).price(2000).build();
    
    when(productRepository.findBy(any(Category.class), eq(SearchOrder.HIGHEST_PRICE)))
        .thenReturn(Optional.of(highestProduct));
    
    Optional<ProductDto> product = productSearchService.searchCategory(Category.TOPS, SearchOrder.HIGHEST_PRICE).get();

    assertNotNull(product);
    assertEquals(highestProduct, product.get());
  }

  @Test
  @DisplayName("카테고리 최고가격 검색 실패 - DB 에러")
  public void getHighestPricedByCateogryFailDbError() throws Exception {
    
    when(productRepository.findBy(any(Category.class), any(SearchOrder.class)))
        .thenThrow(new DatabaseException());
    
    ExecutionException ex = assertThrows(ExecutionException.class, () -> {
      productSearchService.searchCategory(Category.TOPS, SearchOrder.HIGHEST_PRICE).get();
    });
    assertEquals(DatabaseException.class, ex.getCause().getClass());
  }

  @Test
  @DisplayName("최저가격 세트 검색 성공 - Cache 사용")
  public void getLowestPricedSetSuccessWithCache() {
    ProductSet lowestProducts = ProductSet.of(
      ProductDto.builder().brandName(BRAND_A).category(Category.TOPS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_B).category(Category.OUTER).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.PANTS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_B).category(Category.SNEAKERS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.BAGS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_B).category(Category.HATS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.SOCKS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_B).category(Category.ACCESSORIES).price(1000).build());

    cacheProperties.setActive(true);
    
    when(cacheStorage.get(anyString(), any()))
        .thenReturn(Optional.of(lowestProducts));

    ProductSet productSet = productSearchService.searchSet(SearchOrder.LOWEST_PRICE);

    assertNotNull(productSet);
    assertThat(productSet).containsExactlyInAnyOrderElementsOf(lowestProducts);
  }

  @Test
  @DisplayName("한 브랜드 최저가격 세트 검색 성공 - Cache 사용")
  public void getLowestPricedSetForSingleBrandSuccessWithCache() {
    ProductSet lowestProducts = ProductSet.of(
      ProductDto.builder().brandName(BRAND_A).category(Category.TOPS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.OUTER).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.PANTS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.SNEAKERS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.BAGS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.HATS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.SOCKS).price(1000).build(),
      ProductDto.builder().brandName(BRAND_A).category(Category.ACCESSORIES).price(1000).build());
    
    cacheProperties.setActive(true);

    when(cacheStorage.get(anyString(), any()))
        .thenReturn(Optional.of(lowestProducts));

    ProductSet productSet = productSearchService.searchSetForSingleBrand(SearchOrder.LOWEST_PRICE);

    assertNotNull(productSet);
    assertThat(productSet).containsExactlyInAnyOrderElementsOf(lowestProducts);
  }

  @Test
  @DisplayName("카테고리 최저가격 검색 성공 - Cache 사용")
  public void getLowestPricedByCateogrySuccessWithCache() throws Exception {
    ProductDto lowestProduct = ProductDto.builder().brandName(BRAND_A).category(Category.TOPS).price(1000).build();

    cacheProperties.setActive(true);

    when(cacheStorage.get(anyString(), any()))
        .thenReturn(Optional.of(Optional.of(lowestProduct)));
    
    Optional<ProductDto> product = productSearchService.searchCategory(Category.TOPS, SearchOrder.LOWEST_PRICE).get();

    assertNotNull(product);
    assertEquals(lowestProduct, product.get());
  }


  @Test
  @DisplayName("카테고리 최고가격 검색 성공 - Cache 사용")
  public void getHighestPricedByCateogrySuccessWithCache() throws Exception {
    ProductDto highestProduct = ProductDto.builder().brandName(BRAND_A).category(Category.TOPS).price(2000).build();
    
    cacheProperties.setActive(true);

    when(cacheStorage.get(anyString(), any()))
        .thenReturn(Optional.of(Optional.of(highestProduct)));
    
    Optional<ProductDto> product = productSearchService.searchCategory(Category.TOPS, SearchOrder.HIGHEST_PRICE).get();

    assertNotNull(product);
    assertEquals(highestProduct, product.get());
  }

  @Test
  @DisplayName("BrandEvent로 인한 Cache 삭제")
  public void clearCache() {
    ProductSet lowestProducts = ProductSet.of(
      ProductDto.builder().brandName(BRAND_B).category(Category.ACCESSORIES).price(1000).build());

    cacheProperties.setActive(true);
    
    when(cacheStorage.get(anyString(), any()))
        .thenReturn(Optional.empty());
    when(productRepository.findSet(eq(SearchOrder.LOWEST_PRICE)))
        .thenReturn(lowestProducts);
    
    ProductSet productSet = productSearchService.searchSet(SearchOrder.LOWEST_PRICE);

    assertNotNull(productSet);
    assertThat(productSet).containsExactlyInAnyOrderElementsOf(lowestProducts);

    productSearchService.onEvent(BrandEvent.CREATED);

    verify(cacheStorage, times(1)).remove(any(String.class));
  }

}
