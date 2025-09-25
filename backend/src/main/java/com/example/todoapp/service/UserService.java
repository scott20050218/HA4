package com.example.todoapp.service;

import com.example.todoapp.model.User;
import java.util.Optional;

public interface UserService {
    User register(String username, String password);
    Optional<User> login(String username, String password);
    Optional<User> getByUsername(String username);
}
