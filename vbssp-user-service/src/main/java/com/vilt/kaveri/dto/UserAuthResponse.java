package com.vilt.kaveri.dto;
import lombok.Data;

import java.util.Set;

@Data
public class UserAuthResponse {

    private Long userId;
    private String userName;
    private String email;
    private String password_hash;
    private Set<String> roles;
    private String status;
}
