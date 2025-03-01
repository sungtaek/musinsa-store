package com.musinsa.store.product.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.musinsa.store.product.domain.Product;
import com.musinsa.store.product.domain.Brand;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.repository.entity.BrandEntity;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JpaProductRepositoryTest {

  private JpaProductRepositoryAdapter jpaProductRepositoryAdapter;

  @Autowired
  private JpaBrandRepository jpaBrandRepository;

  @Autowired
  private JpaProductRepository jpaProductRepository;

  private record CategoryProduct(Category category, String brandName, Integer price) {
  }

  private static final String[] BRAND_NAMES = new String[] {
      "A", "B", "C", "D", "E", "F", "G", "H", "I"
  };

  private static final Integer[][] BRAND_PRODUCTS = new Integer[][] {
      new Integer[] { 11200, 5500, 4200, 9000, 2000, 1700, 1800, 2300 },
      new Integer[] { 10500, 5900, 3800, 9100, 2100, 2000, 2000, 2200 },
      new Integer[] { 10000, 6200, 3300, 9200, 2200, 1900, 2200, 2100 },
      new Integer[] { 10100, 5100, 3000, 9500, 2500, 1500, 2400, 2000 },
      new Integer[] { 10700, 5000, 3800, 9900, 2300, 1800, 2100, 2100 },
      new Integer[] { 11200, 7200, 4000, 9300, 2100, 1600, 2300, 1900 },
      new Integer[] { 10500, 5800, 3900, 9000, 2200, 1700, 2100, 2000 },
      new Integer[] { 10800, 6300, 3100, 9700, 2100, 1600, 2000, 2000 },
      new Integer[] { 11400, 6700, 3200, 9500, 2400, 1700, 1700, 2400 },
  };

  @BeforeEach
  public void before() {
    jpaProductRepositoryAdapter = new JpaProductRepositoryAdapter(jpaProductRepository);
    for (int i = 0; i < BRAND_NAMES.length; i++) {
      Brand brand = Brand.builder()
          .name(BRAND_NAMES[i])
          .build();
      for (int j = 0; j < BRAND_PRODUCTS[i].length; j++) {
        brand.getProductSet().add(Product.builder()
            .brand(brand)
            .category(Category.values()[j])
            .price(BRAND_PRODUCTS[i][j])
            .build());
      }
      jpaBrandRepository.save(BrandEntity.of(brand));
    }
  }

  @Test
  @DisplayName("최저가 검색")
  public void getLowestPricedSet() {
    List<CategoryProduct> expected = List.of(
        new CategoryProduct(Category.TOPS, "C", 10000),
        new CategoryProduct(Category.OUTER, "E", 5000),
        new CategoryProduct(Category.PANTS, "D", 3000),
        new CategoryProduct(Category.SNEAKERS, "A", 9000),
        new CategoryProduct(Category.BAGS, "A", 2000),
        new CategoryProduct(Category.HATS, "D", 1500),
        new CategoryProduct(Category.SOCKS, "I", 1700),
        new CategoryProduct(Category.ACCESSORIES, "F", 1900));

    List<Product> products = jpaProductRepositoryAdapter.getLowestPricedSet();
    List<CategoryProduct> result = products.stream()
        .map(p -> new CategoryProduct(p.getCategory(), p.getBrand().getName(), p.getPrice()))
        .toList();

    assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
  }

  @Test
  @DisplayName("최저가 브랜드 검색")
  public void getLowestPricedSetForSingleBrand() {
    List<CategoryProduct> expected = List.of(
        new CategoryProduct(Category.TOPS, "D", 10100),
        new CategoryProduct(Category.OUTER, "D", 5100),
        new CategoryProduct(Category.PANTS, "D", 3000),
        new CategoryProduct(Category.SNEAKERS, "D", 9500),
        new CategoryProduct(Category.BAGS, "D", 2500),
        new CategoryProduct(Category.HATS, "D", 1500),
        new CategoryProduct(Category.SOCKS, "D", 2400),
        new CategoryProduct(Category.ACCESSORIES, "D", 2000));

    List<Product> products = jpaProductRepositoryAdapter.getLowestPricedSetForSingleBrand();
    List<CategoryProduct> result = products.stream()
        .map(p -> new CategoryProduct(p.getCategory(), p.getBrand().getName(), p.getPrice()))
        .toList();

    assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
  }

  @Test
  @DisplayName("카테고리 최저가 검색")
  public void getLowestPricedByCategory() {
    Optional<Product> product = jpaProductRepositoryAdapter.getLowestPricedBy(Category.BAGS);

    assertNotNull(product);
    assertEquals("A", product.get().getBrand().getName());
    assertEquals(2000, product.get().getPrice());
  }

  @Test
  @DisplayName("카테고리 최고가 검색")
  public void getHighestPricedByCategory() {
    Optional<Product> product = jpaProductRepositoryAdapter.getHighestPricedBy(Category.BAGS);

    assertNotNull(product);
    assertEquals("D", product.get().getBrand().getName());
    assertEquals(2500, product.get().getPrice());
  }

}
