package com.shristikhadka.demo.repo;

import com.shristikhadka.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String username);
    void deleteByUserName(String username);


}
