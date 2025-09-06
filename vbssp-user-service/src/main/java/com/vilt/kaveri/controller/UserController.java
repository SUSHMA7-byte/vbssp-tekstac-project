package com.vilt.kaveri.controller;

import com.vilt.kaveri.config.UserSecurity;
import com.vilt.kaveri.dto.CreateUserRequest;
import com.vilt.kaveri.dto.UpdateUserRequest;
import com.vilt.kaveri.dto.UserAuthResponse;
import com.vilt.kaveri.dto.UserResponse;
import com.vilt.kaveri.model.PlatformUser;
import com.vilt.kaveri.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserSecurity userSecurity;

    public UserController(UserService userService, UserSecurity userSecurity) {
        this.userService = userService;
        this.userSecurity = userSecurity;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        PlatformUser user = userService.createUser(request);
        return ResponseEntity.ok(userService.toUserResponse(user));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @userSecurity.isSelf(#id, authentication.name)")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        PlatformUser user = userService.getUser(id);
        return ResponseEntity.ok(userService.toUserResponse(user));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserAuthResponse> getUserByEmail(@PathVariable String email) {
        PlatformUser user = userService.getUserByEmail(email);
        return ResponseEntity.ok(userService.toAuthUserResponse(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @userSecurity.isSelf(#id, authentication.name)")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateUserRequest request) {
        PlatformUser user = userService.updateUser(id, request);
        return ResponseEntity.ok(userService.toUserResponse(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
