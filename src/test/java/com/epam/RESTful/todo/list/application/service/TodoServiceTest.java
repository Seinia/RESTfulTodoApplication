package com.epam.RESTful.todo.list.application.service;

import com.epam.RESTful.todo.list.application.model.Todo;
import com.epam.RESTful.todo.list.application.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTodos() {
        Todo todo1 = new Todo(1L, "Task 1", "Description 1");
        Todo todo2 = new Todo(2L, "Task 2", "Description 2");

        when(todoRepository.findAll()).thenReturn(List.of(todo1, todo2));

        List<Todo> todos = todoService.getAllTodos();

        assertEquals(2, todos.size());
        assertEquals("Task 1", todos.get(0).getTitle());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void testGetTodoByIdFound() {
        Todo todo = new Todo(1L, "Task 1", "Description 1");

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        Optional<Todo> result = todoService.getTodoById(1L);

        assertTrue(result.isPresent());
        assertEquals("Task 1", result.get().getTitle());
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTodoByIdNotFound() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Todo> result = todoService.getTodoById(1L);

        assertFalse(result.isPresent());
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveTodo() {
        Todo todo = new Todo(null, "New Task", "New Description");
        Todo savedTodo = new Todo(1L, "New Task", "New Description");

        when(todoRepository.save(todo)).thenReturn(savedTodo);

        Todo result = todoService.saveTodo(todo);

        assertNotNull(result.getId());
        assertEquals("New Task", result.getTitle());
        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    void testDeleteTodoById() {
        doNothing().when(todoRepository).deleteById(1L);

        todoService.deleteTodoById(1L);

        verify(todoRepository, times(1)).deleteById(1L);
    }
}
