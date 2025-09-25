package com.example.todoapp.controller;

import com.example.todoapp.model.User;
import com.example.todoapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth API")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Map<String, Object> register(@RequestBody Map<String, String> req) {
        User user = userService.register(req.get("username"), req.get("password"));
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 201);
        resp.put("message", "Todo register successfully");
        resp.put("data", user);
        return resp;
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Map<String, Object> login(@RequestBody Map<String, String> req) {
        var userOpt = userService.login(req.get("username"), req.get("password"));
        Map<String, Object> resp = new HashMap<>();
        if (userOpt.isPresent()) {
            resp.put("code", 200);
            resp.put("message", "Todo login successfully");
            resp.put("data", Map.of("access_token", "mock-token", "token_type", "bearer"));
        } else {
            resp.put("code", 422);
            resp.put("message", "Invalid username or password");
        }
        return resp;
    }

    @GetMapping("/me")
    @Operation(summary = "获取当前用户信息")
    public Map<String, Object> me() {
        String temp="admin";
        var userOpt = userService.getByUsername(temp);
        Map<String, Object> resp = new HashMap<>();
        if (userOpt.isPresent()) {
            resp.put("code", 200);
            resp.put("message", "Todo me successfully");
            resp.put("data", userOpt.get());
        } else {
            resp.put("code", 422);
            resp.put("message", "User not found");
        }
        return resp;
    }
}
