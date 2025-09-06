
package com.vilt.kaveri.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vilt.kaveri.dto.*;

@Component
public class UserServiceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public UserServiceClient(RestTemplate restTemplate,
                             @Value("${user.service.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    public UserResponse getUserById(Long id) {
        String url = baseUrl + "/api/users/" + id;
        return restTemplate.getForObject(url, UserResponse.class);
    }

    public UserAuthResponse getUserByEmail(String email) {
        String url = baseUrl + "/api/users/email/" + email;
        return restTemplate.getForObject(url, UserAuthResponse.class);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        String url = baseUrl + "/api/users/" + id;
        ResponseEntity<UserResponse> resp =
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request), UserResponse.class);
        return resp.getBody();
    }

    public void deleteUser(Long id) {
        String url = baseUrl + "/api/users/" + id;
        restTemplate.delete(url);
    }
}
