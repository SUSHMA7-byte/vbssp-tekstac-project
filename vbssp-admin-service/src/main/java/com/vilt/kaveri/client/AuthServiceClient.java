package com.vilt.kaveri.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vilt.kaveri.dto.AuthRequest;

@Component
public class AuthServiceClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public AuthServiceClient(RestTemplate restTemplate,
                             @Value("${auth.service.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    @SuppressWarnings("unchecked")
    public String login(AuthRequest request) {
        String url = baseUrl + "/auth/login";
        ResponseEntity<Map> resp = restTemplate.postForEntity(url, request, Map.class);
        Object token = resp.getBody() != null ? resp.getBody().get("token") : null;
        return token != null ? token.toString() : null;
    }

    public String validate(String token) {
        String url = baseUrl + "/auth/validate?token=" + token;
        return restTemplate.getForObject(url, String.class);
    }
}
