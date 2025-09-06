package com.vilt.kaveri.service;

import org.springframework.stereotype.Service;

import com.vilt.kaveri.aop.LoggableAction;
import com.vilt.kaveri.client.AuthServiceClient;
import com.vilt.kaveri.client.UserServiceClient;
import com.vilt.kaveri.dto.AuthRequest;
import com.vilt.kaveri.dto.UpdateUserRequest;
import com.vilt.kaveri.dto.UserAuthResponse;
import com.vilt.kaveri.dto.UserResponse;

@Service
public class AdminService {

    private final UserServiceClient userClient;
    private final AuthServiceClient authClient;
    private final AuditLogService auditLogService;

    public AdminService(UserServiceClient userClient, AuthServiceClient authClient, AuditLogService auditLogService) {
        this.userClient = userClient;
        this.authClient = authClient;
        this.auditLogService = auditLogService;
    }

    // ------------ USER MANAGEMENT (via User Service) ------------
    @LoggableAction(action = "GET_USER", entity = "User")
    public UserResponse getUser(Long adminId, Long userId) {
        return userClient.getUserById(userId);
    }

    @LoggableAction(action = "GET_USER_BY_EMAIL", entity = "User")
    public UserAuthResponse getUserByEmail(Long adminId, String email) {
        return userClient.getUserByEmail(email);
    }

    @LoggableAction(action = "UPDATE_USER", entity = "User")
    public UserResponse updateUser(Long adminId, Long userId, UpdateUserRequest request) {
        return userClient.updateUser(userId, request);
    }

    @LoggableAction(action = "DELETE_USER", entity = "User")
    public void deleteUser(Long adminId, Long userId) {
        userClient.deleteUser(userId);
    }
    
    @LoggableAction(action = "UPDATE_USER_ROLE", entity = "User")
    public UserResponse updateUserRole(Long adminId, Long userId, String newRole) {
        UpdateUserRequest request = UpdateUserRequest.builder()
                .role(newRole)
                .build();

        return userClient.updateUser(userId, request);
    }


    // ------------ AUTH (via Authentication Service) ------------
    @LoggableAction(action = "LOGIN_AS_USER", entity = "Auth")
    public String loginAsUser(String email, String password) {
        return authClient.login(new AuthRequest() {{
            setEmail(email);
            setPassword(password);
        }});
    }

    @LoggableAction(action = "VALIDATE_TOKEN", entity = "Auth")
    public String validateToken(String token) {
        return authClient.validate(token);
    }

    // -------------------- PROVIDERS / REVIEWS (stubs for now) --------------------
    @LoggableAction(action = "APPROVE_PROVIDER", entity = "ServiceProvider")
    public void approveProvider(Long adminId, Long providerId) { /* TODO: integrate provider service */ }

    @LoggableAction(action = "REJECT_PROVIDER", entity = "ServiceProvider")
    public void rejectProvider(Long adminId, Long providerId) { /* TODO */ }

    @LoggableAction(action = "DELETE_PROVIDER", entity = "ServiceProvider")
    public void deleteProvider(Long adminId, Long providerId) { /* TODO */ }

    @LoggableAction(action = "DELETE_REVIEW", entity = "Review")
    public void deleteReview(Long adminId, Long reviewId) { /* TODO */ }
}
