package com.musinsa.store.product.exception;

import com.musinsa.store.common.exception.InvalidRequestException;
import com.musinsa.store.common.exception.ResultCode;

public class InvalidBrandException extends InvalidRequestException {

  public InvalidBrandException(String message) {
    super(message);
  }

  public InvalidBrandException(String message, Throwable t) {
    super(message, t);
  }

  @Override
  public ResultCode getResultCode() {
    return ResultCode.INVALID_PARAMETER;
  }

}
