package com.vilt.kaveri.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vilt.kaveri.dto.UpdateUserRequest;
import com.vilt.kaveri.dto.UserAuthResponse;
import com.vilt.kaveri.dto.UserResponse;
import com.vilt.kaveri.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ---------------- USER MANAGEMENT ----------------

    @GetMapping("/users/{userId}")
    public UserResponse getUser(@RequestParam Long adminId, @PathVariable Long userId) {
        return adminService.getUser(adminId, userId);
    }

    @GetMapping("/users/email/{email}")
    public UserAuthResponse getUserByEmail(@RequestParam Long adminId, @PathVariable String email) {
        return adminService.getUserByEmail(adminId, email);
    }

    @PutMapping("/users/{userId}")
    public UserResponse updateUser(
            @RequestParam Long adminId,
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request) {
        return adminService.updateUser(adminId, userId, request);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@RequestParam Long adminId, @PathVariable Long userId) {
        adminService.deleteUser(adminId, userId);
    }
    
    @PutMapping("/users/{userId}/role")
    public UserResponse updateUserRole(
            @RequestParam Long adminId,
            @PathVariable Long userId,
            @RequestParam String role) {
        return adminService.updateUserRole(adminId, userId, role);
    }

    // ---------------- AUTH ----------------

    @PostMapping("/login-as-user")
    public String loginAsUser(@RequestParam String email, @RequestParam String password) {
        return adminService.loginAsUser(email, password);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam String token) {
        return adminService.validateToken(token);
    }

    // ---------------- PROVIDER MANAGEMENT (TODO integrate service) ----------------

    @PostMapping("/providers/{providerId}/approve")
    public void approveProvider(@RequestParam Long adminId, @PathVariable Long providerId) {
        adminService.approveProvider(adminId, providerId);
    }

    @PostMapping("/providers/{providerId}/reject")
    public void rejectProvider(@RequestParam Long adminId, @PathVariable Long providerId) {
        adminService.rejectProvider(adminId, providerId);
    }

    @DeleteMapping("/providers/{providerId}")
    public void deleteProvider(@RequestParam Long adminId, @PathVariable Long providerId) {
        adminService.deleteProvider(adminId, providerId);
    }

    // ---------------- REVIEW MANAGEMENT ----------------

    @DeleteMapping("/reviews/{reviewId}")
    public void deleteReview(@RequestParam Long adminId, @PathVariable Long reviewId) {
        adminService.deleteReview(adminId, reviewId);
    }
}
