package com.project.ecommerce.controller;

import com.project.ecommerce.model.Users;
import com.project.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by email ID
    @GetMapping("/{emailId}")
    public ResponseEntity<Users> getUserByEmailId(@PathVariable String emailId) {
        Optional<Users> user = userService.getUserByEmailId(emailId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Update an existing user
    @PutMapping("/{emailId}")
    public ResponseEntity<Users> updateUser(@PathVariable String emailId, @RequestBody Users user) {
        Optional<Users> existingUser = userService.getUserByEmailId(emailId);
        if (existingUser.isPresent()) {
            user.setUserEmailId(emailId); // Ensure the emailId doesn't change
            Users updatedUser = userService.saveUser(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a user
    @DeleteMapping("/{emailId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String emailId) {
        Optional<Users> existingUser = userService.getUserByEmailId(emailId);
        if (existingUser.isPresent()) {
            userService.deleteUser(emailId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
