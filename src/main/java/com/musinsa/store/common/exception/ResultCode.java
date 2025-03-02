package com.musinsa.store.common.exception;

import lombok.Getter;

@Getter
public enum ResultCode {
  SUCCESS("0000", "Success"),
  
  // invalid request exception
  INVALID_REQUEST("1000", "Invalid request"),
  INVALID_PARAMETER("1001", "Invalid parameter"),

  // not found exception
  NOT_FOUND("1100", "Not found"),

  // internal exception
  INTERNAL_ERROR("2000", "Internal error"),
  DATABASE_ERROR("2001", "Database error"),
  ;

  private String code;
  private String defaultMessage;

  private ResultCode(String code, String defaultMessage) {
    this.code = code;
    this.defaultMessage = defaultMessage;
  }
}
