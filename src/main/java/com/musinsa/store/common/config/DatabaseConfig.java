package com.musinsa.store.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
@EnableJpaAuditing
public class DatabaseConfig {
  
  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory queryFactory() {
    return new JPAQueryFactory(entityManager);
  }
}
