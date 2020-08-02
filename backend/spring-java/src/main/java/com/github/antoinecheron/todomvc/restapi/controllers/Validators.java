package com.github.antoinecheron.todomvc.restapi.controllers;

import java.time.LocalDate;
import java.util.Optional;

import reactor.core.publisher.Mono;

import com.github.antoinecheron.todomvc.restapi.models.ApiError;
import com.github.antoinecheron.todomvc.commons.models.Status;
import com.github.antoinecheron.todomvc.restapi.models.TodoCreationRequest;
import com.github.antoinecheron.todomvc.restapi.models.TodoUpdateRequest;

final class Validators {

  private Validators() {}

  static Mono<TodoCreationRequest> validateTodoCreationRequest (TodoCreationRequest toValidate) {
    if (isValid(toValidate.getTitle()) && isValid(toValidate.getDueDate())) {
      return Mono.just(toValidate);
    } else {
      return Mono.error(new ApiError("Incorrect title or dueDate, they must not be empty, null neither blank", 400));
    }
  }

  static Mono<TodoUpdateRequest> validateTodoUpdateRequest (TodoUpdateRequest toValidate) {
    if (isValid(toValidate.getTitle())) {
      return Mono.just(toValidate);
    } else {
      return Mono.error(new ApiError("Incorrect title, it must not be empty neither blank", 400));
    }
  }

  static Mono<Status> validateStatus(String status, Status defaultValue) {
    return Status.of(Optional.ofNullable(status).orElse(defaultValue.name()).toUpperCase())
      .map(Mono::just)
      .orElseGet(() ->
        Mono.error(new ApiError("Incorrect status, it must be \"all\" or \"completed\" or \"active\".", 400)));
  }

  private static boolean isValid(String s) {
    return !s.isBlank() && !s.isEmpty();
  }

  private static boolean isValid(LocalDate d) {
    return d != null;
  }

}
