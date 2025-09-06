package com.vilt.kaveri.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    private String userName;
    private String phoneNum;
    private String password;
    private String role;
}
