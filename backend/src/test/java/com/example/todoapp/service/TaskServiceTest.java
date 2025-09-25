package com.example.todoapp.service;

import com.example.todoapp.model.Task;
import com.example.todoapp.repository.TaskRepository;
import com.example.todoapp.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        Task task = new Task();
        task.setTitle("Test");
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task created = taskService.createTask(task);
        assertEquals("Test", created.getTitle());
    }

    @Test
    void testGetAllTasks() {
        Task task = new Task();
        task.setTitle("Test");
        List<Task> tasks = Arrays.asList(task);
        Page<Task> page = new PageImpl<>(tasks);
        when(taskRepository.findAll(any(PageRequest.class))).thenReturn(page);
        Page<Task> result = taskService.getAllTasks(null, PageRequest.of(0, 10));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        task.setId(1);
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        Optional<Task> found = taskService.getTaskById(1);
        assertTrue(found.isPresent());
        assertEquals(1, found.get().getId());
    }

    @Test
    void testUpdateTask() {
        Task old = new Task();
        old.setId(1);
        old.setTitle("Old");
        Task update = new Task();
        update.setTitle("New");
        update.setDescription("desc");
        update.setCompleted(true);
        update.setPriority(2);
        update.setDueDate(LocalDateTime.now());
        when(taskRepository.findById(1)).thenReturn(Optional.of(old));
        when(taskRepository.save(any(Task.class))).thenReturn(update);
        Task result = taskService.updateTask(1, update);
        assertEquals("New", result.getTitle());
        assertTrue(result.getCompleted());
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1);
        assertDoesNotThrow(() -> taskService.deleteTask(1));
    }

    @Test
    void testToggleTask() {
        Task task = new Task();
        task.setId(1);
        task.setCompleted(false);
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        Task toggled = taskService.toggleTask(1);
        assertTrue(toggled.getCompleted());
    }

    @Test
    void testDeleteCompletedTasks() {
        when(taskRepository.deleteByCompleted(true)).thenReturn(3L);
        long count = taskService.deleteCompletedTasks();
        assertEquals(3L, count);
    }

    @Test
    void testDeleteAllTasks() {
        when(taskRepository.count()).thenReturn(5L);
        doNothing().when(taskRepository).deleteAll();
        long count = taskService.deleteAllTasks();
        assertEquals(5L, count);
    }
}
