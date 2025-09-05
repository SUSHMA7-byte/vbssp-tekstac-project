package com.vilt.kaveri.controller;

import com.vilt.kaveri.dto.CreateUserRequest;
import com.vilt.kaveri.dto.UpdateUserRequest;
import com.vilt.kaveri.dto.UserAuthResponse;
import com.vilt.kaveri.dto.UserResponse;
import com.vilt.kaveri.model.PlatformUser;
import com.vilt.kaveri.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // --- Create User ---
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        PlatformUser user = userService.createUser(request);
        return ResponseEntity.ok(userService.toUserResponse(user));
    }

    // --- Get single User ---
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        PlatformUser user = userService.getUser(id);
        return ResponseEntity.ok(userService.toUserResponse(user));
    }

    // --- Get all Users ---
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserAuthResponse> getUserByEmail(@PathVariable String email) {
        PlatformUser user = userService.getUserByEmail(email);
        return ResponseEntity.ok(userService.toAuthUserResponse(user));
    }

    // --- Update User ---
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        PlatformUser user = userService.updateUser(id, request);
        return ResponseEntity.ok(userService.toUserResponse(user));
    }

    // --- Delete User ---
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
