package com.vilt.kaveri.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRequest {

    @Size(min = 3, max = 50, message = "Username must be 3-50 characters")
    private String userName;

    @Size(min = 10, message = "Phone number should have minimum 10 characters")
    private String phoneNum;

    private String password;

    private Set<String> roles;
}

