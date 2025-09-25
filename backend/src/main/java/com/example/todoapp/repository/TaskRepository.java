package com.example.todoapp.repository;

import com.example.todoapp.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Page<Task> findByCompleted(Boolean completed, Pageable pageable);
    long deleteByCompleted(Boolean completed);
}
