package com.vilt.kaveri.feign;

import com.vilt.kaveri.dto.UserAuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-management-service", url = "http://localhost:8081")
public interface UserServiceClient {

    @GetMapping("/api/users/email/{email}")
    UserAuthResponse getUserByEmail(@PathVariable("email") String email);
}
