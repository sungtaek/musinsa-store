package com.musinsa.store.common.exception;

public class InternalException extends ServiceException {

  public InternalException() {
    super(ResultCode.UNKNOWN_ERROR.getDefaultMessage());
  }

  public InternalException(Throwable t) {
    super(t.getMessage(), t);
  }

  public InternalException(String message) {
    super(message);
  }

  public InternalException(String message, Throwable t) {
    super(message, t);
  }

  public ResultCode getResultCode() {
    return ResultCode.UNKNOWN_ERROR;
  }

}
