package com.musinsa.store.product.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.musinsa.store.product.domain.Brand;
import com.musinsa.store.product.domain.Category;
import com.musinsa.store.product.domain.Product;
import com.musinsa.store.product.repository.entity.BrandEntity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JpaBrandRepositoryTest {

  private JpaBrandRepositoryAdapter jpaBrandRepositoryAdapter;

  @Autowired
  private JpaBrandRepository jpaBrandRepository;

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
  @DisplayName("Brand 저장")
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
    assertEquals(3, brand.getProducts().size());
    brand.getProducts().stream()
        .forEach(p -> assertNotNull(p.getId()));
  }

  @Test
  @DisplayName("Brand 체크")
  public void isExist() {
    Long brandId = jpaBrandRepository.save(BrandEntity.of(BRAND)).getId();

    assertTrue(jpaBrandRepositoryAdapter.isExist(brandId));
    assertFalse(jpaBrandRepositoryAdapter.isExist(10000L));
  }

  @Test
  @DisplayName("Brand 조회")
  public void get() {
    Long brandId = jpaBrandRepository.save(BrandEntity.of(BRAND)).getId();

    assertTrue(jpaBrandRepositoryAdapter.get(brandId).isPresent());
    assertFalse(jpaBrandRepositoryAdapter.get(1000L).isPresent());
  }

  @Test
  @DisplayName("Brand 삭제")
  public void delete() {
    Long brandId = jpaBrandRepository.save(BrandEntity.of(BRAND)).getId();

    jpaBrandRepositoryAdapter.delete(brandId);

    assertFalse(jpaBrandRepository.existsById(brandId));
  }

}
