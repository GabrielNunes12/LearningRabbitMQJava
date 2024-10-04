package com.example.kafkaJourny.controllers;

import com.example.kafkaJourny.dtos.UserDTO;
import com.example.kafkaJourny.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/api/v1/users")
    public ResponseEntity<?> addUser(@RequestBody UserDTO user) {
        return ResponseEntity.ok().body(userService.saveUser(user));
    }
}
