package com.vilt.kaveri.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class UserAuthResponse {

    private Long userId;
    private String userName;
    private String email;

    @JsonProperty("password_hash")
    private String password_hash;

    private Set<String> roles;
    private String status;
}
