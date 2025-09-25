package com.example.todoapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {
    @GetMapping("/api/v1/health")
    public Map<String, Object> health() {
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 200);
        resp.put("message", "ok");
        return resp;
    }
}
