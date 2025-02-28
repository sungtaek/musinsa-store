package com.musinsa.store.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musinsa.store.product.repository.entity.BrandEntity;

public interface JpaBrandRepository extends JpaRepository<BrandEntity, Long> {
  
}
