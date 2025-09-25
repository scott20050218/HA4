package com.example.todoapp.service.impl;

import com.example.todoapp.model.Task;
import com.example.todoapp.repository.TaskRepository;
import com.example.todoapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Page<Task> getAllTasks(Boolean completed, Pageable pageable) {
        if (completed == null) {
            return taskRepository.findAll(pageable);
        }
        return taskRepository.findByCompleted(completed, pageable);
    }

    @Override
    public Optional<Task> getTaskById(Integer id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task createTask(Task task) {
        task.setId(null);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Integer id, Task task) {
        Task old = taskRepository.findById(id).orElseThrow();
        System.out.println("updateTask status: " + old);
        System.out.println("updateTask status: " + task);
        old.setTitle(task.getTitle());
        old.setDescription(task.getDescription());
        old.setCompleted(task.getCompleted());
        old.setPriority(task.getPriority());
        old.setDueDate(task.getDueDate());
        return taskRepository.save(old);
    }

    @Override
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task toggleTask(Integer id) {
        Task task = taskRepository.findById(id).orElseThrow();
        System.out.println("Current completed status: " + task);
        task.setCompleted(!task.getCompleted());
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public long deleteCompletedTasks() {
        return taskRepository.deleteByCompleted(true);
    }

    @Override
    @Transactional
    public long deleteAllTasks() {
        long count = taskRepository.count();
        taskRepository.deleteAll();
        return count;
    }
}
