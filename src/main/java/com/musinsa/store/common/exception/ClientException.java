package com.musinsa.store.common.exception;

public class ClientException extends ServiceException {

  public ClientException() {
    super(ResultCode.INVALID_REQUEST.getDefaultMessage());
  }

  public ClientException(Throwable t) {
    super(t.getMessage(), t);
  }

  public ClientException(String message) {
    super(message);
  }

  public ClientException(String message, Throwable t) {
    super(message, t);
  }

  public ResultCode getResultCode() {
    return ResultCode.INVALID_REQUEST;
  }

}
