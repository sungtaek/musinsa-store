package com.musinsa.store.common.exception;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.musinsa.store.common.dto.ResponsePayload;

import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  @SuppressWarnings("null")
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    log.warn(ex.getMessage());

    BindingResult bindingResult = ex.getBindingResult();
    String message = ex.getMessage();
    Optional<FieldError> optionalFieldError = bindingResult.getFieldErrors().stream().findFirst();
    if (optionalFieldError.isPresent()) {
      FieldError fieldError = optionalFieldError.get();
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      sb.append(fieldError.getField());
      sb.append("] : ");
      sb.append(fieldError.getDefaultMessage());
      message = sb.toString();
    }

    ResultCode result = ResultCode.INVALID_PARAMETER;
    ResponsePayload<Void> response = ResponsePayload.<Void>builder()
        .code(result.getCode())
        .message(message)
        .build();
    return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
  }

  @Override
  @SuppressWarnings("null")
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    log.warn(ex.getMessage());

    String message = String.format("Invalid type: cannot convert value '%s' to type [%s]",
        (ex.getValue() != null) ? String.valueOf(ex.getValue()) : "null",
        ex.getRequiredType().getSimpleName());

    ResultCode result = ResultCode.INVALID_PARAMETER;
    ResponsePayload<Void> response = ResponsePayload.<Void>builder()
        .code(result.getCode())
        .message(message)
        .build();
    return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
	}

  @Override
  @SuppressWarnings("null")
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    log.warn(ex.getMessage());

    ResultCode result = ResultCode.INVALID_PARAMETER;
    ResponsePayload<Void> response = ResponsePayload.<Void>builder()
        .code(result.getCode())
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
  }

  @Override
  @SuppressWarnings("null")
  protected ResponseEntity<Object> handleHandlerMethodValidationException(
      HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    log.warn(ex.getMessage(), ex);

    StringBuilder sb = new StringBuilder();
    ex.getParameterValidationResults().stream()
        .flatMap(result -> result.getResolvableErrors().stream())
        .findFirst()
        .ifPresent(err -> {
          if (err.getCodes() != null && err.getCodes().length > 0) {
            sb.append(err.getCodes()[0] + ": ");
          }
          sb.append(err.getDefaultMessage());
        });
    String error = sb.toString();
    
    ResultCode result = ResultCode.INVALID_PARAMETER;
    ResponsePayload<Void> response = ResponsePayload.<Void>builder()
        .code(result.getCode())
        .message(error.isEmpty() ? ex.getMessage() : error)
        .build();
    return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
  }

  @Override
  @SuppressWarnings("null")
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    log.warn(ex.getMessage());

    ResultCode result = ResultCode.INTERNAL_ERROR;
    ResponsePayload<Void> response = ResponsePayload.<Void>builder()
        .code(result.getCode())
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
  }


  @ExceptionHandler(ServiceException.class)
  @ResponseBody
  public ResponseEntity<Object> handleServiceException(HttpServletRequest request, ServiceException ex) {
    ResultCode result = ResultCode.INTERNAL_ERROR;
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    if (ex instanceof InvalidRequestException) {
      result = ((InvalidRequestException) ex).getResultCode();
      httpStatus = HttpStatus.BAD_REQUEST;
      log.warn(ex.getMessage());
    } else if (ex instanceof NotFoundException) {
      result = ((NotFoundException) ex).getResultCode();
      httpStatus = HttpStatus.NOT_FOUND;
      log.warn(ex.getMessage());
    } else if (ex instanceof InternalException) {
      result = ((InternalException) ex).getResultCode();
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      log.error(ex.getMessage(), ex);
    } else {
      log.error(ex.getMessage(), ex);
    }

    ResponsePayload<Void> response = ResponsePayload.<Void>builder()
        .code(result.getCode())
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(response, httpStatus);
  }

  @ExceptionHandler(value = {ConstraintViolationException.class, MissingRequestHeaderException.class})
  @ResponseBody
  public ResponseEntity<Object> handleValidationException(Exception ex) {
    log.warn(ex.getMessage());

    ResultCode result = ResultCode.INVALID_PARAMETER;
    ResponsePayload<Void> response = ResponsePayload.<Void>builder()
        .code(result.getCode())
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PersistenceException.class)
  @ResponseBody
  public ResponseEntity<Object> handleDatabaseException(HttpServletRequest request, Exception ex) {
    log.error(ex.getMessage(), ex);

    ResultCode result = ResultCode.DATABASE_ERROR;
    ResponsePayload<Void> response = ResponsePayload.<Void>builder()
        .code(result.getCode())
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResponseEntity<Object> handleUnknownException(HttpServletRequest request, Exception ex) {
    log.error(ex.getMessage(), ex);

    ResultCode result = ResultCode.INTERNAL_ERROR;
    ResponsePayload<Void> response = ResponsePayload.<Void>builder()
        .code(result.getCode())
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
}
