package com.musinsa.store.common.exception;

import lombok.Getter;

@Getter
public enum ResultCode {
  SUCCESS("0000", "Success"),
  
  // client exception
  INVALID_REQUEST("1000", "Invalid request"),

  // internal exception
  UNKNOWN_ERROR("2000", "Unknown"),
  DATABASE_ERROR("2001", "Database error"),
  ;

  private String code;
  private String defaultMessage;

  private ResultCode(String code, String defaultMessage) {
    this.code = code;
    this.defaultMessage = defaultMessage;
  }
}
