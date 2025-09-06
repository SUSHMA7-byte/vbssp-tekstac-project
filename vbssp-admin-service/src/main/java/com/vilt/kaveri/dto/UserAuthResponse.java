package com.vilt.kaveri.dto;

import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthResponse {
    private Long id;
    private String email;
    private String password;
    private Set<String> roles;
    private String status;
}
