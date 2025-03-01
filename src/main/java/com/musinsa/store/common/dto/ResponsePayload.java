package com.musinsa.store.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.musinsa.store.common.exception.ResultCode;

import lombok.Builder;
import lombok.Data;
import lombok.Builder.Default;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePayload<T> {
  public static ResponsePayload<Void> SUCCESS = ResponsePayload.<Void>builder().build();

  @Default
  private String code = ResultCode.SUCCESS.getCode();
  @Default
  private String message = ResultCode.SUCCESS.getDefaultMessage();
  private T data;
  
}
