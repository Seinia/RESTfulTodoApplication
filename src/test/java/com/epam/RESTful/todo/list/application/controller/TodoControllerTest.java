package com.epam.RESTful.todo.list.application.controller;

import com.epam.RESTful.todo.list.application.model.Todo;
import com.epam.RESTful.todo.list.application.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTodos() {
        Todo todo1 = new Todo(1L, "Task 1", "Description 1");
        Todo todo2 = new Todo(2L, "Task 2", "Description 2");

        when(todoService.getAllTodos()).thenReturn(List.of(todo1, todo2));

        List<Todo> todos = todoController.getAllTodos();

        assertEquals(2, todos.size());
        assertEquals("Task 1", todos.get(0).getTitle());
        verify(todoService, times(1)).getAllTodos();
    }

    @Test
    void testGetTodoByIdFound() {
        Todo todo = new Todo(1L, "Task 1", "Description 1");

        when(todoService.getTodoById(1L)).thenReturn(Optional.of(todo));

        ResponseEntity<Todo> response = todoController.getTodoById(1L);

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Task 1", response.getBody().getTitle());
        verify(todoService, times(1)).getTodoById(1L);
    }

    @Test
    void testGetTodoByIdNotFound() {
        when(todoService.getTodoById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Todo> response = todoController.getTodoById(1L);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(todoService, times(1)).getTodoById(1L);
    }

    @Test
    void testCreateTodo() {
        Todo todo = new Todo(null, "New Task", "New Description");
        Todo savedTodo = new Todo(1L, "New Task", "New Description");

        when(todoService.saveTodo(todo)).thenReturn(savedTodo);

        Todo response = todoController.createTodo(todo);

        assertNotNull(response.getId());
        assertEquals("New Task", response.getTitle());
        verify(todoService, times(1)).saveTodo(todo);
    }

    @Test
    void testUpdateTodoFound() {
        Todo existingTodo = new Todo(1L, "Old Task", "Old Description");
        Todo updatedTodo = new Todo(null, "Updated Task", "Updated Description");

        when(todoService.getTodoById(1L)).thenReturn(Optional.of(existingTodo));
        when(todoService.saveTodo(any(Todo.class))).thenReturn(existingTodo);

        ResponseEntity<Todo> response = todoController.updateTodo(1L, updatedTodo);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Updated Task", response.getBody().getTitle());
        verify(todoService, times(1)).getTodoById(1L);
        verify(todoService, times(1)).saveTodo(existingTodo);
    }

    @Test
    void testUpdateTodoNotFound() {
        Todo updatedTodo = new Todo(null, "Updated Task", "Updated Description");

        when(todoService.getTodoById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Todo> response = todoController.updateTodo(1L, updatedTodo);

        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(todoService, times(1)).getTodoById(1L);
        verify(todoService, times(0)).saveTodo(any(Todo.class));
    }

    @Test
    void testDeleteTodoByIdFound() {
        when(todoService.getTodoById(1L)).thenReturn(Optional.of(new Todo()));

        ResponseEntity<Void> response = todoController.deleteTodoById(1L);

        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(todoService, times(1)).getTodoById(1L);
        verify(todoService, times(1)).deleteTodoById(1L);
    }

    @Test
    void testDeleteTodoByIdNotFound() {
        when(todoService.getTodoById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = todoController.deleteTodoById(1L);

        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(todoService, times(1)).getTodoById(1L);
        verify(todoService, times(0)).deleteTodoById(anyLong());
    }
}
