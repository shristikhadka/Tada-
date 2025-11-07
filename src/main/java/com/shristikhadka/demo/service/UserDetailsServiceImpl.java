package com.shristikhadka.demo.service;

import com.shristikhadka.demo.entity.User;
import com.shristikhadka.demo.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        
        // Ensure roles are loaded (handle null or empty roles)
        List<String> roles = user.getRoles();
        if (roles == null || roles.isEmpty()) {
            throw new UsernameNotFoundException("User has no roles assigned: " + username);
        }
        
        // Convert roles list to array for Spring Security
        String[] rolesArray = roles.toArray(new String[0]);
        
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
            .username(user.getUserName())
            .password(user.getPassword())
            .roles(rolesArray)
            .build();
        return userDetails;
    }
}
