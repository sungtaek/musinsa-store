package com.musinsa.store.common.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper=false)
public class Page<T> extends ArrayList<T> {
  private int page;
  private int size;
  private int totalPage;

  public Page(int page, int size, int totalPage) {
    super();
    this.page = page;
    this.size = size;
    this.totalPage = totalPage;
  }

  public Page(int page, int size, int totalPage, Collection<T> data) {
    super(data);
    this.page = page;
    this.size = size;
    this.totalPage = totalPage;
  }

  public static <T> Page<T> of(org.springframework.data.domain.Page<T> page) {
    return new Page<>(page.getNumber(), page.getSize(),
        page.getTotalPages(), page.toList());
  }

  public static <T, V> Page<V> of(org.springframework.data.domain.Page<T> page,
      Function<T, V> converter) {
    return new Page<>(page.getNumber(), page.getSize(),
        page.getTotalPages(), page.map(converter).toList());
  }
}
