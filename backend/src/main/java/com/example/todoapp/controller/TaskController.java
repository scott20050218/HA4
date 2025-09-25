package com.example.todoapp.controller;

import com.example.todoapp.model.Task;
import com.example.todoapp.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Todo API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    @Operation(summary = "获取所有待办事项")
    public Map<String, Object> getAll(@RequestParam(required = false) Boolean completed,
                                      @RequestParam(defaultValue = "100") int limit,
                                      @RequestParam(defaultValue = "0") int offset) {
        Page<Task> page = taskService.getAllTasks(completed, PageRequest.of(offset / limit, limit));
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 200);
        resp.put("message", "success");
        resp.put("data", page.getContent());
        resp.put("total", page.getTotalElements());
        return resp;
    }

    @PostMapping
    @Operation(summary = "创建待办事项")
    public Map<String, Object> create(@RequestBody Task task) {
        Task created = taskService.createTask(task);
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 201);
        resp.put("message", "Todo created successfully");
        resp.put("data", created);
        return resp;
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新待办事项")
    public Map<String, Object> update(@PathVariable Integer id, @RequestBody Task task) {
        Task toggled = taskService.toggleTask(id);
        // Task updated = taskService.updateTask(id, task);
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 200);
        resp.put("message", "Todo updated successfully");
        resp.put("data", toggled);
        return resp;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除待办事项")
    public Map<String, Object> delete(@PathVariable Integer id) {
        taskService.deleteTask(id);
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 200);
        resp.put("message", "Todo deleted successfully");
        return resp;
    }

    @PatchMapping("/{id}/toggle")
    @Operation(summary = "切换完成状态")
    public Map<String, Object> toggle(@PathVariable Integer id) {
        Task toggled = taskService.toggleTask(id);
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 200);
        resp.put("message", "Todo status toggled successfully");
        Map<String, Object> data = new HashMap<>();
        data.put("id", toggled.getId());
        data.put("completed", toggled.getCompleted());
        data.put("updated_at", toggled.getUpdatedAt());
        resp.put("data", data);
        return resp;
    }

    @DeleteMapping("/completed")
    @Operation(summary = "批量删除已完成项")
    public Map<String, Object> deleteCompleted() {
        long count = taskService.deleteCompletedTasks();
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 200);
        resp.put("message", "Completed todos deleted successfully");
        resp.put("deleted_count", count);
        return resp;
    }

    @DeleteMapping("/all")
    @Operation(summary = "清空所有待办事项")
    public Map<String, Object> deleteAll() {
        long count = taskService.deleteAllTasks();
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 200);
        resp.put("message", "All todos deleted successfully");
        resp.put("deleted_count", count);
        return resp;
    }
}
