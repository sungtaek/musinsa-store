package com.musinsa.store.product.repository;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.musinsa.store.common.dto.Page;
import com.musinsa.store.common.exception.DatabaseException;
import com.musinsa.store.product.domain.Brand;
import com.musinsa.store.product.domain.BrandRepository;
import com.musinsa.store.product.repository.entity.BrandEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaBrandRepositoryAdapter implements BrandRepository {

  private final JpaBrandRepository repository;

  @Override
  public Page<Brand> list(int page, int size) {
    try {
      return Page.of(repository.findAll(PageRequest.of(page, size)),
          BrandEntity::toBrandWithoutProducts);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public Brand save(Brand brand) {
    try {
      return repository.save(BrandEntity.of(brand))
          .toBrand();
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public boolean isExist(Long id) {
    try {
      return repository.existsById(id);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public Optional<Brand> get(Long id) {
    try {
      return repository.findById(id)
          .map(BrandEntity::toBrand);
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }

  @Override
  public void delete(Long id) {
    try {
      if (repository.existsById(id)) {
        repository.deleteById(id);
      }
    } catch (Exception ex) {
      throw new DatabaseException(ex);
    }
  }
  
}
