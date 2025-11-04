package com.shristikhadka.demo.service;

import com.shristikhadka.demo.entity.Todo;
import com.shristikhadka.demo.entity.User;
import com.shristikhadka.demo.repo.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodosForUser(User user) {
        return user.getTodoList();
    }

    public Todo createTodoForUser(Todo todo, User user) {
        todo.setUser(user);
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo updated, User currentUser) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));

        // Security: Check if the task belongs to this user
        if (!todo.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized: You can only update your own todos");
        }

        todo.setTitle(updated.getTitle());
        todo.setCompleted(updated.isCompleted());
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id, User currentUser) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));

        // Security: Check if the task belongs to this user
        if (!todo.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Unauthorized: You can only delete your own todos");
        }

        todoRepository.deleteById(id);
    }
}
