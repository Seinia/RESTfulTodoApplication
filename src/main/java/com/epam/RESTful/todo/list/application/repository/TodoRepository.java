package com.epam.RESTful.todo.list.application.repository;

import com.epam.RESTful.todo.list.application.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}