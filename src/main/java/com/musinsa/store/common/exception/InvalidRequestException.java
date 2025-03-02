package com.musinsa.store.common.exception;

public class InvalidRequestException extends ServiceException {

  public InvalidRequestException() {
    super(ResultCode.INVALID_REQUEST.getDefaultMessage());
  }

  public InvalidRequestException(Throwable t) {
    super(t.getMessage(), t);
  }

  public InvalidRequestException(String message) {
    super(message);
  }

  public InvalidRequestException(String message, Throwable t) {
    super(message, t);
  }

  public ResultCode getResultCode() {
    return ResultCode.INVALID_REQUEST;
  }

}
