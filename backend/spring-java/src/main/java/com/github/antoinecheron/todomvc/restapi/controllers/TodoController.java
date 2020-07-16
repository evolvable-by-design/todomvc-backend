package com.github.antoinecheron.todomvc.restapi.controllers;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import com.github.antoinecheron.todomvc.commons.models.Todo;
import com.github.antoinecheron.todomvc.restapi.hypermedia.HypermediaRepresentation;
import com.github.antoinecheron.todomvc.restapi.models.ApiError;
import com.github.antoinecheron.todomvc.restapi.models.TodoCreationRequest;
import com.github.antoinecheron.todomvc.restapi.models.TodoUpdateRequest;
import com.github.antoinecheron.todomvc.commons.services.TodoService;

@RestController
@RequestMapping("/rest/todo")
public class TodoController {

  private final TodoService todoService;

  public TodoController(@Autowired TodoService todoService) {
    this.todoService = todoService;
  }

  @PostMapping @ResponseStatus(HttpStatus.CREATED)
  public Mono<HypermediaRepresentation<Todo>> createTodo (@RequestBody Publisher<TodoCreationRequest> todoCreationRequestStream) {
    return Mono.fromDirect(todoCreationRequestStream)
      .flatMap(Validators::validateTodoCreationRequest)
      .flatMap(todoCreationRequest -> todoService.create(todoCreationRequest.getTitle(), todoCreationRequest.getDueDate()))
      .map(TodoController::addHypermediaControls);
  }

  @PutMapping("/{id}")
  public Mono<HypermediaRepresentation<Todo>> updateTodo(@PathVariable String id, @RequestBody Publisher<TodoUpdateRequest> todoUpdateRequestStream) {
    return Mono.fromDirect(todoUpdateRequestStream)
      .flatMap(Validators::validateTodoUpdateRequest)
      .flatMap(todoUpdateRequest ->
        todoService.findById(id)
          .map(existingTodo -> Tuples.of(existingTodo, todoUpdateRequest))
          .switchIfEmpty(Mono.error(new ApiError("Todo with id " + id + " not found.", 404)))
      )
      .flatMap(tuple -> {
        final var todo = tuple.getT1();
        final var todoUpdateRequest = tuple.getT2();

        return todoService.update(id, todoUpdateRequest.getTitle(), todoUpdateRequest.isCompleted(), todo.getDueDate());
      })
      .map(TodoController::addHypermediaControls);
  }

  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteTodo(@PathVariable String id) {
    return this.todoService.delete(id);
  }

  public static HypermediaRepresentation<Todo> addHypermediaControls(Todo todo) {
    return HypermediaRepresentation.Builder.of(todo)
      .withLink("update")
      .withLink("delete")
      .withLink("listAll")
      .build();
  }

}
