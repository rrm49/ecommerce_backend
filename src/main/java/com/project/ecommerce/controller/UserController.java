package com.project.ecommerce.controller;

import com.project.ecommerce.model.Message;
import com.project.ecommerce.model.Users;
import com.project.ecommerce.model.dtos.LoginRequest;
import com.project.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "User")
@RequestMapping("v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // Register new User
    @PostMapping("/register")
    @Operation(summary = "Register", security = {})
    public ResponseEntity<Object> createUser(@RequestBody LoginRequest loginRequest) {
        loginRequest.setUserPassword(encoder.encode(loginRequest.getUserPassword()));
        Optional<Users> existingUser = userService.getUserByEmailId(loginRequest.getUserEmailId());
        if (existingUser.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Message.getErrorMsg("A user with email " + loginRequest.getUserEmailId() + " already exists."));

        }
        Users savedUser = userService.saveUser(loginRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(savedUser);
    }

    // Login User
    @PostMapping("/login")
    @Operation(summary = "Login", security = {})
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getUserName() == null && loginRequest.getUserEmailId() != null) {
            Optional<Users> enrichUser = userService.getUserByEmailId(loginRequest.getUserEmailId());
            if (enrichUser.isPresent()) {
                loginRequest.setUserName(enrichUser.get().getUserName());
                return userService.verify(loginRequest);
            } else {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Message.getErrorMsg("Email not found"));
            }
        }
        return userService.verify(loginRequest);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        response.sendRedirect("/");  // Redirect after logout
    }


    // Get all users
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/users")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }
}
