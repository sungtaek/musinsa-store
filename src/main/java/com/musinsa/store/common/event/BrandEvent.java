package com.musinsa.store.common.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandEvent {
  private Type type;

  public static enum Type {
    CREATED,
    UPDATED,
    DELETED,
  }

  public static final BrandEvent CREATED = BrandEvent.builder().type(Type.CREATED).build();
  public static final BrandEvent UPDATED = BrandEvent.builder().type(Type.UPDATED).build();
  public static final BrandEvent DELETED = BrandEvent.builder().type(Type.DELETED).build();
}
