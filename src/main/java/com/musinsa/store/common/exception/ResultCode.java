package com.musinsa.store.common.exception;

import lombok.Getter;

@Getter
public enum ResultCode {
  SUCCESS("0000", "Success"),
  
  // client exception
  INVALID_PARAMETER("1001", "Ivnalid parameter"),
  INVALID_REQUEST("1900", "Invalid request"),

  // internal exception
  DATABASE_ERROR("2001", "Database error"),
  UNKNOWN_ERROR("2900", "Unknown"),
  ;

  private String code;
  private String defaultMessage;

  private ResultCode(String code, String defaultMessage) {
    this.code = code;
    this.defaultMessage = defaultMessage;
  }
}
