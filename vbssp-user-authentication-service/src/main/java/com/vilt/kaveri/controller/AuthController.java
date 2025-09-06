package com.vilt.kaveri.controller;

import com.vilt.kaveri.config.JwtUtil;
import com.vilt.kaveri.dto.AuthRequest;
import com.vilt.kaveri.dto.UserAuthResponse;
import com.vilt.kaveri.feign.UserServiceClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserServiceClient userServiceClient,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userServiceClient = userServiceClient;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody AuthRequest request) {
        UserAuthResponse user;
        try {
            user = userServiceClient.getUserByEmail(request.getEmail());
            if (user == null) return Map.of("message", "User not found");
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("message", "User service unavailable");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword_hash())) {
            return Map.of("message", "Incorrect password");
        }

        Set<String> roles = user.getRoles();
        String token = jwtUtil.generateToken(user.getEmail(), roles);

        return Map.of(
                "token", token,
                "user", Map.of(
                        "userId", user.getUserId(),
                        "userName", user.getUserName(),
                        "email", user.getEmail(),
                        "roles", roles,
                        "status", user.getStatus()
                )
        );
    }

    @GetMapping("/validate")
    public Map<String, Object> validateToken(@RequestParam String token) {
        try {
            String email = jwtUtil.getUsername(token);
            Set<String> roles = jwtUtil.getRoles(token);
            return Map.of(
                    "message", "Token valid",
                    "email", email,
                    "roles", roles
            );
        } catch (Exception e) {
            return Map.of("message", "Invalid token");
        }
    }
}
