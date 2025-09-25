package com.example.todoapp.controller;

import com.example.todoapp.model.Task;
import com.example.todoapp.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAll() throws Exception {
        Task task = new Task();
        task.setId(1);
        task.setTitle("Test");
        Mockito.when(taskService.getAllTasks(any(), any())).thenReturn(new PageImpl<>(Collections.singletonList(task)));
        mockMvc.perform(get("/api/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testCreate() throws Exception {
        Task task = new Task();
        task.setTitle("Test");
        Mockito.when(taskService.createTask(any(Task.class))).thenReturn(task);
        mockMvc.perform(post("/api/v1/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(201));
    }

    @Test
    void testUpdate() throws Exception {
        Task task = new Task();
        task.setId(1);
        task.setTitle("Updated");
        Mockito.when(taskService.updateTask(eq(1), any(Task.class))).thenReturn(task);
        mockMvc.perform(put("/api/v1/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testToggle() throws Exception {
        Task task = new Task();
        task.setId(1);
        task.setCompleted(true);
        Mockito.when(taskService.toggleTask(1)).thenReturn(task);
        mockMvc.perform(patch("/api/v1/todos/1/toggle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testDeleteCompleted() throws Exception {
        Mockito.when(taskService.deleteCompletedTasks()).thenReturn(2L);
        mockMvc.perform(delete("/api/v1/todos/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void testDeleteAll() throws Exception {
        Mockito.when(taskService.deleteAllTasks()).thenReturn(5L);
        mockMvc.perform(delete("/api/v1/todos/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }
}
