package com.github.antoinecheron.todomvc.restapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.github.antoinecheron.todomvc.restapi.models.ApiError;
import com.github.antoinecheron.todomvc.restapi.models.ErrorPayload;

@ControllerAdvice
public class GlobalErrorHandler {

  @ExceptionHandler(ApiError.class)
  public ResponseEntity<ErrorPayload> handleApiErrorException(ApiError error) {
    return ResponseEntity
      .status(error.code)
      .body(new ErrorPayload(error.getMessage()));
  }

}
