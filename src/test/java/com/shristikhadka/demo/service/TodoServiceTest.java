package com.shristikhadka.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shristikhadka.demo.entity.Todo;
import com.shristikhadka.demo.entity.User;
import com.shristikhadka.demo.repo.TodoRepository;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private User user;
    private Todo todo;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        user=new User();
        user.setId(1L);

        todo=new Todo();
        todo.setId(10L);
        todo.setTitle("Buy groceries");
        todo.setCompleted(false);
        todo.setUser(user);
    }
    @Test
    void createTodoForUser_shouldAssignUserAndSave(){
        
    }
    
}
