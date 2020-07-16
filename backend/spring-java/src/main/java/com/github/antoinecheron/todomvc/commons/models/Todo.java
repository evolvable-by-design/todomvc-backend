package com.github.antoinecheron.todomvc.commons.models;

import java.time.LocalDate;

import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
public class Todo {

  @Id
  private final String id;
  private final String title;
  private final boolean completed;
  private final LocalDate dueDate;

}
