package com.shristikhadka.demo.controller;

import com.shristikhadka.demo.entity.Todo;
import com.shristikhadka.demo.entity.User;
import com.shristikhadka.demo.service.TodoService;
import com.shristikhadka.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user/todos")
public class TodoController
{
    private final TodoService todoService;
    private final UserService userService;

    public TodoController(TodoService todoService, UserService userService){
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping
    public List<Todo> getAll(Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        return todoService.getAllTodosForUser(currentUser);
    }

    @PostMapping
    public Todo create(@RequestBody Todo todo, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        return todoService.createTodoForUser(todo, currentUser);
    }

    @PutMapping("/{id}")
    public Todo update(@PathVariable Long id, @RequestBody Todo updated, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        return todoService.updateTodo(id, updated, currentUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        todoService.deleteTodo(id, currentUser);
    }

}
