package com.example.todoapp.service;

import com.example.todoapp.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TaskService {
    Page<Task> getAllTasks(Boolean completed, Pageable pageable);
    Optional<Task> getTaskById(Integer id);
    Task createTask(Task task);
    Task updateTask(Integer id, Task task);
    void deleteTask(Integer id);
    Task toggleTask(Integer id);
    long deleteCompletedTasks();
    long deleteAllTasks();
}
