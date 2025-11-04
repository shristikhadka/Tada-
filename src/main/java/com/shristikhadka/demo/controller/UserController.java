package com.shristikhadka.demo.controller;

import com.shristikhadka.demo.dto.PasswordChangeRequest;
import com.shristikhadka.demo.entity.User;
import com.shristikhadka.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User>getCurrentUser(Principal principal){
        User user=userService.getUserByUsername(principal.getName());
        return ResponseEntity.status(200).body(user);
    }
    @PutMapping("/me")
    public ResponseEntity<User> updateProfile(Principal principal, @RequestBody User updatedUser){
        User currentUser=userService.getUserByUsername(principal.getName());
        User updated=userService.updateUser(currentUser.getId(),updatedUser);
        return ResponseEntity.status(200).body(updated);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void>deleteProfile(Principal principal){
        User currentUser=userService.getUserByUsername(principal.getName());
        userService.deleteUser(currentUser.getId());
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/change-password")
    public ResponseEntity<String>changePassword(Principal principal, @RequestBody PasswordChangeRequest request){
        User currentUser=userService.getUserByUsername(principal.getName());
        userService.changePassword(currentUser.getId(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok("Password changed successfully");
    }
}
