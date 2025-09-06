package com.vilt.kaveri.config;

import com.vilt.kaveri.service.UserService;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {

    private final UserService userService;

    public UserSecurity(UserService userService) {
        this.userService = userService;
    }

    /**
     * Check if the given id belongs to the authenticated user (by email).
     */
    public boolean isSelf(Long userId, String authEmail) {
        String userEmail = userService.getUser(userId).getEmail();
        return userEmail.equalsIgnoreCase(authEmail);
    }
}

