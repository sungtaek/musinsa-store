package com.musinsa.store.common.exception;

public class NotFoundException extends ServiceException {

  public NotFoundException() {
    super(ResultCode.NOT_FOUND.getDefaultMessage());
  }

  public NotFoundException(Throwable t) {
    super(t.getMessage(), t);
  }

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, Throwable t) {
    super(message, t);
  }

  public ResultCode getResultCode() {
    return ResultCode.NOT_FOUND;
  }
}
