package com.shristikhadka.demo.repo;

import com.shristikhadka.demo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TodoRepository extends JpaRepository<Todo,Long> {}

