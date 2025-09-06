package com.vilt.kaveri.dto;
import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String userName;
    private String phoneNum;
    private String role;
    private String status;
}
