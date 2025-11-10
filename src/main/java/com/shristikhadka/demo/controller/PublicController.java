package com.shristikhadka.demo.controller;

import com.shristikhadka.demo.entity.User;
import com.shristikhadka.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController
{

    private final UserService userService;

    public PublicController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/register")
    public ResponseEntity<User>registerUser(@RequestBody User user){
        log.info("creating new user: {}",user.getUserName());
        User createdUser=userService.saveNewUser(user);
        return ResponseEntity.status(201).body(createdUser);
    }
}
