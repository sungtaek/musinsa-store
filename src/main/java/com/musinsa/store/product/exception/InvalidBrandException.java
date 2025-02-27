package com.musinsa.store.product.exception;

import com.musinsa.store.common.exception.ClientException;

public class InvalidBrandException extends ClientException {

  public InvalidBrandException(String message) {
    super(message);
  }

  public InvalidBrandException(String message, Throwable t) {
    super(message, t);
  }
}
