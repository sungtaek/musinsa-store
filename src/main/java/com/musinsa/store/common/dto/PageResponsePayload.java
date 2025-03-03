package com.musinsa.store.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.store.common.exception.ResultCode;

import lombok.Builder;
import lombok.Data;
import lombok.Builder.Default;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponsePayload<T> {
  @Default
  private String code = ResultCode.SUCCESS.getCode();
  @Default
  private String message = ResultCode.SUCCESS.getDefaultMessage();
  @Default
  private List<T> data = new ArrayList<>();
  private Integer page;
  private Integer size;
  private Integer totalPage;
}
