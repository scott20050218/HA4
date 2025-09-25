
package com.example.todoapp.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todoapp.model.User;
import com.example.todoapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;


    @Test
    public void testRegisterSuccess() {
        User user = new User();
        user.setUsername("testuser");
        when(userService.register(anyString(), anyString())).thenReturn(user);
        Map<String, String> req = new HashMap<>();
        req.put("username", "testuser");
        req.put("password", "pass");
        Map<String, Object> response = authController.register(req);
        assertEquals(201, response.get("code"));
        assertEquals("Todo register successfully", response.get("message"));
        assertEquals(user, response.get("data"));
    }

    @Test
    public void testRegisterDuplicate() {
        when(userService.register(anyString(), anyString())).thenThrow(new RuntimeException("User exists"));
        Map<String, String> req = new HashMap<>();
        req.put("username", "testuser");
        req.put("password", "pass");
        try {
            authController.register(req);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("User exists", e.getMessage());
        }
    }

    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setUsername("testuser");
        when(userService.login(anyString(), anyString())).thenReturn(Optional.of(user));
        Map<String, String> req = new HashMap<>();
        req.put("username", "testuser");
        req.put("password", "pass");
        Map<String, Object> response = authController.login(req);
        assertEquals(200, response.get("code"));
        assertEquals("Todo login successfully", response.get("message"));
        assertNotNull(response.get("data"));
    }

    @Test
    public void testLoginFail() {
        when(userService.login(anyString(), anyString())).thenReturn(Optional.empty());
        Map<String, String> req = new HashMap<>();
        req.put("username", "testuser");
        req.put("password", "wrong");
        Map<String, Object> response = authController.login(req);
        assertEquals(422, response.get("code"));
        assertEquals("Invalid username or password", response.get("message"));
    }

    // @Test
    // public void testMeFound() {
    //     User user = new User();
    //     user.setUsername("testuser");
    //     when(userService.getByUsername("testuser")).thenReturn(Optional.of(user));
    //     Map<String, Object> response = authController.me("testuser");
    //     assertEquals(200, response.get("code"));
    //     assertEquals("Todo me successfully", response.get("message"));
    //     assertEquals(user, response.get("data"));
    // }

    // @Test
    // public void testMeNotFound() {
    //     when(userService.getByUsername("testuser")).thenReturn(Optional.empty());
    //     Map<String, Object> response = authController.me("testuser");
    //     assertEquals(422, response.get("code"));
    //     assertEquals("User not found", response.get("message"));
    // }
}
