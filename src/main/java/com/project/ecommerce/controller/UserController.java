package com.project.ecommerce.controller;

import com.project.ecommerce.model.Message;
import com.project.ecommerce.model.Users;
import com.project.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // Register new User
    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody Users user) {
        user.setUserPassword(encoder.encode(user.getUserPassword()));
        Optional<Users> existingUser = userService.getUserByEmailId(user.getUserEmailId());
        if (existingUser.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Message.getErrorMsg("A user with email " + user.getUserEmailId() + " already exists."));

        }
        Users savedUser = userService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(savedUser);
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Users user) {
        if (user.getUserName() == null && user.getUserEmailId() != null) {
            Optional<Users> enrichUser = userService.getUserByEmailId(user.getUserEmailId());
            if (enrichUser.isPresent()) {
                user.setUserName(enrichUser.get().getUserName());
                return userService.verify(user);
            } else {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Message.getErrorMsg("Email not found"));
            }
        }
        return userService.verify(user);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    // Get user by email ID
    @GetMapping("/{emailId}")
    public ResponseEntity<Users> getUserByEmailId(@PathVariable String emailId) {
        Optional<Users> user = userService.getUserByEmailId(emailId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
