package com.musinsa.store.common.exception;

public class DatabaseException extends InternalException {

  public DatabaseException() {
    super(ResultCode.DATABASE_ERROR.getDefaultMessage());
  }

  public DatabaseException(Throwable t) {
    super(t.getMessage(), t);
  }

  public DatabaseException(String message) {
    super(message);
  }

  public DatabaseException(String message, Throwable t) {
    super(message, t);
  }

  public ResultCode getResultCode() {
    return ResultCode.DATABASE_ERROR;
  }

}
