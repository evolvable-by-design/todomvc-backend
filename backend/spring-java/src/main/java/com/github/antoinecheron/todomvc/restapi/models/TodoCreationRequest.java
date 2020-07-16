package com.github.antoinecheron.todomvc.restapi.models;

import java.time.LocalDate;

import lombok.Value;

@Value
public class TodoCreationRequest {

  private final String title;
  private final LocalDate dueDate;

}
