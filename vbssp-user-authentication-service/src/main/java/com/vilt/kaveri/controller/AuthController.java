package com.vilt.kaveri.controller;

import java.util.Map;

import com.vilt.kaveri.config.JwtUtil;
import com.vilt.kaveri.dto.AuthRequest;
import com.vilt.kaveri.dto.UserAuthResponse;
import com.vilt.kaveri.feign.UserServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AuthRequest request) {

        UserAuthResponse user;
        try {
            user = userServiceClient.getUserByEmail(request.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword_hash())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRoles());
        return Map.of("token", token);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam String token) {
        try {
            return "Token valid for user: " + jwtUtil.getUsername(token);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }
}

