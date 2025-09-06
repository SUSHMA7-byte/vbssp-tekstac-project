package com.vilt.kaveri.service;

import com.vilt.kaveri.dto.CreateUserRequest;
import com.vilt.kaveri.dto.UpdateUserRequest;
import com.vilt.kaveri.dto.UserAuthResponse;
import com.vilt.kaveri.dto.UserResponse;
import com.vilt.kaveri.exception.ResourceNotFoundException;
import com.vilt.kaveri.model.PlatformUser;
import com.vilt.kaveri.model.Role;
import com.vilt.kaveri.repository.RoleRepository;
import com.vilt.kaveri.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public PlatformUser createUser(CreateUserRequest request) {
        PlatformUser user = new PlatformUser();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword_hash(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNum(request.getPhoneNum());
        user.setStatus(PlatformUser.Status.ACTIVE);

        Role defaultRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found: ROLE_USER"));
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public PlatformUser getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    public PlatformUser getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    public PlatformUser updateUser(Long userId, UpdateUserRequest request) {
        PlatformUser user = getUser(userId);

        if (request.getUserName() != null) user.setUserName(request.getUserName());
        if (request.getPhoneNum() != null) user.setPhoneNum(request.getPhoneNum());

        if (request.getRoles() != null) {
            Set<Role> roles = request.getRoles().stream()
                    .map(roleName -> roleRepository.findByRoleName(roleName)
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse toUserResponse(PlatformUser user) {
        return new UserResponse(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getPhoneNum(),
                user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()),
                user.getStatus().name()
        );
    }

    public UserAuthResponse toAuthUserResponse(PlatformUser user) {
        UserAuthResponse response = new UserAuthResponse();
        response.setUserId(user.getUserId());
        response.setUserName(user.getUserName());
        response.setEmail(user.getEmail());
        response.setPassword_hash(user.getPassword_hash());
        response.setRoles(user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()));
        response.setStatus(user.getStatus().name());
        return response;
    }

}
