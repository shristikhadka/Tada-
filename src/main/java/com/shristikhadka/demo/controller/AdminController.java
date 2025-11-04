package com.shristikhadka.demo.controller;
import com.shristikhadka.demo.dto.PasswordChangeRequest;
import com.shristikhadka.demo.entity.User;
import com.shristikhadka.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        log.info("Fetching all users");
       List<User>users= userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User>getUserById(@PathVariable Long id){
        log.info("Fetching user with id{}",id);
        Optional<User> userOptional=userService.getUserById(id);
        if(userOptional.isPresent()){
            User user=userOptional.get();
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void>deleteUser(@PathVariable Long id){
        log.info("Deleting user with id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        log.info("Creating user {} with roles: {}", user.getUserName(), user.getRoles());
        User createdUser = userService.createUserWithRoles(user, user.getRoles());
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/users/{id}/roles")
    public ResponseEntity<User> updateUserRoles(@PathVariable Long id, @RequestBody List<String> roles){
        log.info("Updating roles for user {}: {}", id, roles);
        try{
            User updatedUser = userService.updateUserRoles(id, roles);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            log.warn("Failed to update roles for user {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/users/{id}/password")
    public ResponseEntity<String> resetUserPassword(@PathVariable Long id, @RequestBody PasswordChangeRequest request){
        log.info("Admin resetting password for user {}", id);
        try{
            userService.updatePassword(id, request.getNewPassword());
            return ResponseEntity.ok("Password reset successfully");
        } catch (RuntimeException e) {
            log.warn("Failed to reset password for user {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
