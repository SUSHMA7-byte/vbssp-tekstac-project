package com.vilt.kaveri.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class CreateUserRequest {
    @NotBlank(message = "Username is required")
    @Size(min=3, max=50, message = "Username must be 3-50 characters")
    private String userName;

    @NotNull(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min=8, message = "Password must be at least 8 characters")
    private String password;

    @Size(min=10, message = "Phone number should have minimum 10 characters")
    private String phoneNum;
}

