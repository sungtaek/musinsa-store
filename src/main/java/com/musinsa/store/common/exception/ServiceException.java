package com.musinsa.store.common.exception;

public class ServiceException extends RuntimeException {
  
  public ServiceException() {
    super();
  }

  public ServiceException(Throwable t) {
    super(t);
  }

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable t) {
    super(message, t);
  }
}
