package com.shristikhadka.demo.repo;

import com.shristikhadka.demo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.shristikhadka.demo.entity.User;


public interface TodoRepository extends JpaRepository<Todo,Long> {
    Page<Todo> findByUser(User user, Pageable page);
}

