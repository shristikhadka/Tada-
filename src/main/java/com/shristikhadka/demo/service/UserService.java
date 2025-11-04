package com.shristikhadka.demo.service;

import com.shristikhadka.demo.entity.User;
import com.shristikhadka.demo.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //crud
    public List<User> getAllUsers(){
       return userRepository.findAll();
    }
    public Optional<User> getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            log.warn("User not found with id {}", id);
        }
        return user;
    }
    public User getUserByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }
    public void deleteUser(Long id){
        try{
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.info("Unable to delete user with id: "+id);
        }
    }

    //create user only for internal use
    public User saveUser(User user){
        return userRepository.save(user);
    }
    //create new user
    public User saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            return userRepository.save(user);
        } catch (Exception e) {
            log.info("new user not saved "+e);
            throw new RuntimeException("Failed to register user");
        }
    }
    //update existing user
    public User updateUser(Long id, User updateUser){
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        if (updateUser.getUserName() != null) {
            existingUser.setUserName(updateUser.getUserName());
        }
        return userRepository.save(existingUser);
    }

    //updat use for admin to be able to change roles
    public User updateUserRoles(Long id,List<String> roles){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        user.setRoles(roles);
        return userRepository.save(user);
    }


    //update password for admin  forget pwd
    public void updatePassword(Long id,String newPassword){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    //for user
    public void changePassword(Long userId,String oldPassword,String newPassword){
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found"));

        if(!passwordEncoder.matches(oldPassword,user.getPassword())){
            throw new RuntimeException("Old password is not correct");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }

    // For admin to create users with specific roles
    public User createUserWithRoles(User user, List<String> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
