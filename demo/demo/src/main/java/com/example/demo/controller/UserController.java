package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody UserDTO userDTO) {
        Map<String, Object> response = userService.register(userDTO);
        HttpStatus status = HttpStatus.valueOf((Integer) response.get("statusCode"));
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDTO) {
        Map<String, Object> response = userService.login(userDTO);
        HttpStatus status = HttpStatus.valueOf((Integer) response.get("statusCode"));
        return new ResponseEntity<>(response, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        Map<String, Object> response = userService.deleteUser(id);
        HttpStatus status = HttpStatus.valueOf((Integer) response.get("statusCode"));
        return new ResponseEntity<>(response, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Map<String, Object> response = userService.updateUser(id, userDTO);
        HttpStatus status = HttpStatus.valueOf((Integer) response.get("statusCode"));
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the User Login & Signup API!";
    }
}