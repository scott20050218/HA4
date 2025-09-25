
package com.example.todoapp.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todoapp.model.User;
import com.example.todoapp.repository.UserRepository;
import com.example.todoapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void testRegisterSuccess() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        User user = userService.register("testuser", "pass");
        assertEquals("testuser", user.getUsername());
        assertEquals("user", user.getRole());
    }

    @Test
    public void testRegisterDuplicate() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(new User()));
        assertThrows(RuntimeException.class, () -> userService.register("testuser", "pass"));
    }

    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("pass");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        Optional<User> result = userService.login("testuser", "pass");
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    public void testLoginFail() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        Optional<User> result = userService.login("testuser", "wrong");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetByUsername() {
        User user = new User();
        user.setUsername("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        Optional<User> result = userService.getByUsername("testuser");
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }
}
