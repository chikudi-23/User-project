package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    @Override
    public Map<String, Object> register(UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();
        //  response.put("input", userDTO);  // Removed "input"
        response.put("timestamp", getCurrentTimestamp());

        Optional<User> existing = userRepository.findByUsername(userDTO.getUsername());
        if (existing.isPresent()) {
            response.put("status", "error");
            response.put("message", "User already exists!");
            response.put("statusCode", HttpStatus.CONFLICT.value());
        } else {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            userRepository.save(user);
            response.put("status", "success");
            response.put("message", "User registered successfully!");
            response.put("statusCode", HttpStatus.CREATED.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> login(UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();
        // response.put("input", userDTO);  // Removed "input"
        response.put("timestamp", getCurrentTimestamp());

        Optional<User> userOpt = userRepository.findByUsername(userDTO.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(userDTO.getPassword())) {
                response.put("status", "success");
                response.put("message", "Login successful!");
                response.put("statusCode", HttpStatus.OK.value());
            } else {
                response.put("status", "error");
                response.put("message", "Invalid password!");
                response.put("statusCode", HttpStatus.UNAUTHORIZED.value());
            }
        } else {
            response.put("status", "error");
            response.put("message", "User not found!");
            response.put("statusCode", HttpStatus.NOT_FOUND.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> deleteUser(Long id) {
        Map<String, Object> response = new HashMap<>();
        // response.put("input", Map.of("id", id));  // Removed "input"
        response.put("timestamp", getCurrentTimestamp());

        if (!userRepository.existsById(id)) {
            response.put("status", "error");
            response.put("message", "User with ID " + id + " not found");
            response.put("statusCode", HttpStatus.NOT_FOUND.value());
        } else {
            userRepository.deleteById(id);
            response.put("status", "success");
            response.put("message", "User deleted successfully");
            response.put("statusCode", HttpStatus.OK.value());
        }
        return response;
    }

    @Override
    public Map<String, Object> updateUser(Long id, UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();
        //  response.put("input", userDTO);  // Removed "input"
        response.put("timestamp", getCurrentTimestamp());

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            response.put("status", "error");
            response.put("message", "User with ID " + id + " not found");
            response.put("statusCode", HttpStatus.NOT_FOUND.value());
        } else {
            User existingUser = userOptional.get();

            if (userDTO.getUsername() != null) {
                existingUser.setUsername(userDTO.getUsername());
            }
            if (userDTO.getPassword() != null) {
                existingUser.setPassword(userDTO.getPassword());
            }

            userRepository.save(existingUser);
            response.put("status", "success");
            response.put("message", "User updated successfully");
            response.put("statusCode", HttpStatus.OK.value());
        }
        return response;
    }
}