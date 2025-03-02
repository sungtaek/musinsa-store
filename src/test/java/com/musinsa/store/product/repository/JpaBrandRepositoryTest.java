package com.musinsa.store.product.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.musinsa.store.product.domain.Brand;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.Product;
import com.musinsa.store.product.repository.entity.BrandEntity;

@DataJpaTest
public class JpaBrandRepositoryTest {

  private JpaBrandRepositoryAdapter jpaBrandRepositoryAdapter;

  @Autowired
  private JpaBrandRepository jpaBrandRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  private static final Brand BRAND = Brand.builder()
      .name("A")
      .products(List.of(
          Product.builder().category(Category.OUTER).price(1000).build(),
          Product.builder().category(Category.BAGS).price(1000).build(),
          Product.builder().category(Category.ACCESSORIES).price(1000).build()))
      .build();

  @BeforeEach
  public void before() {
    jpaBrandRepositoryAdapter = new JpaBrandRepositoryAdapter(jpaBrandRepository);
  }

  @Test
  @DisplayName("브랜드 저장")
  public void save() {

    Brand brand = Brand.builder()
        .name("test")
        .products(List.of(
            Product.builder().category(Category.OUTER).price(1000).build(),
            Product.builder().category(Category.BAGS).price(1000).build(),
            Product.builder().category(Category.ACCESSORIES).price(1000).build()))
        .build();

    brand = jpaBrandRepositoryAdapter.save(brand);

    assertNotNull(brand);
    assertNotNull(brand.getId());
    assertEquals(3, brand.getProductSet().size());
    brand.getProductSet().stream()
        .forEach(p -> assertNotNull(p.getId()));
  }

  @Test
  @DisplayName("브랜드 체크")
  public void isExist() {
    BrandEntity brand = testEntityManager.persist(BrandEntity.of(BRAND));

    assertTrue(jpaBrandRepositoryAdapter.isExist(brand.getId()));
    assertFalse(jpaBrandRepositoryAdapter.isExist(10000L));
  }

  @Test
  @DisplayName("브랜드 조회")
  public void get() {
    BrandEntity brand = testEntityManager.persist(BrandEntity.of(BRAND));

    assertTrue(jpaBrandRepositoryAdapter.get(brand.getId()).isPresent());
    assertFalse(jpaBrandRepositoryAdapter.get(1000L).isPresent());
  }

  @Test
  @DisplayName("브랜드 삭제")
  public void delete() {
    BrandEntity brand = testEntityManager.persist(BrandEntity.of(BRAND));

    jpaBrandRepositoryAdapter.delete(brand.getId());

    assertNull(testEntityManager.find(BrandEntity.class, brand.getId()));
  }

}
