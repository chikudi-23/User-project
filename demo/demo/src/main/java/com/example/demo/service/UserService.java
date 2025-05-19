package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import java.util.Map;

public interface UserService {
    Map<String, Object> register(UserDTO userDTO);
    Map<String, Object> login(UserDTO userDTO);
    Map<String, Object> deleteUser(Long id);  // Changed return type
    Map<String, Object> updateUser(Long id, UserDTO userDTO); // Changed return type
}