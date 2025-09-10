package com.vilt.kaveri.config;

import com.vilt.kaveri.exception.UnauthorizedActionException;
import com.vilt.kaveri.model.PlatformUser;
import com.vilt.kaveri.service.UserService;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {

    private final UserService userService;

    public UserSecurity(UserService userService) {
        this.userService = userService;
    }

    public boolean isSelf(Long userId, String authEmail) {
        PlatformUser user = userService.getUser(userId);
        if (!user.getEmail().equalsIgnoreCase(authEmail)) {
            throw new UnauthorizedActionException("You are not authorized to access this profile");
        }
        return true;
    }

}

