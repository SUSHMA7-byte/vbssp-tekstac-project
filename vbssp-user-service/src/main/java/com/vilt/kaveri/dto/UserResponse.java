package com.vilt.kaveri.dto;

import lombok.*;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String userName;
    private String email;
    private String phoneNum;
    private Set<String> roles;
    private String status;
}
