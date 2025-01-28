package com.epam.RESTful.todo.list.application.repository;

import com.epam.RESTful.todo.list.application.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Ensures `application-test.properties` is used
@Transactional // Ensures the database is rolled back after each test
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void testSaveTodo() {
        Todo todo = new Todo(null, "Task 1", "Description 1");

        Todo savedTodo = todoRepository.save(todo);

        assertNotNull(savedTodo.getId());
        assertEquals("Task 1", savedTodo.getTitle());
        assertEquals("Description 1", savedTodo.getDescription());
    }

    @Test
    void testFindById() {
        Todo todo = new Todo(null, "Task 1", "Description 1");
        Todo savedTodo = todoRepository.save(todo);

        Optional<Todo> result = todoRepository.findById(savedTodo.getId());

        assertTrue(result.isPresent());
        assertEquals("Task 1", result.get().getTitle());
    }

    @Test
    void testFindAll() {
        Todo todo1 = new Todo(null, "Task 1", "Description 1");
        Todo todo2 = new Todo(null, "Task 2", "Description 2");
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        List<Todo> todos = todoRepository.findAll();

        assertEquals(2, todos.size());
        assertEquals("Task 1", todos.get(0).getTitle());
        assertEquals("Task 2", todos.get(1).getTitle());
    }

    @Test
    void testDeleteById() {
        Todo todo = new Todo(null, "Task 1", "Description 1");
        Todo savedTodo = todoRepository.save(todo);

        todoRepository.deleteById(savedTodo.getId());

        Optional<Todo> result = todoRepository.findById(savedTodo.getId());

        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteAll() {
        Todo todo1 = new Todo(null, "Task 1", "Description 1");
        Todo todo2 = new Todo(null, "Task 2", "Description 2");
        todoRepository.save(todo1);
        todoRepository.save(todo2);

        todoRepository.deleteAll();

        List<Todo> todos = todoRepository.findAll();

        assertTrue(todos.isEmpty());
    }
}