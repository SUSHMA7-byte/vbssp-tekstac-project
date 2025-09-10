package com.vilt.kaveri.service;

import com.vilt.kaveri.dto.CreateUserRequest;
import com.vilt.kaveri.dto.UpdateUserRequest;
import com.vilt.kaveri.dto.UserAuthResponse;
import com.vilt.kaveri.dto.UserResponse;
import com.vilt.kaveri.exception.EmailAlreadyExistsException;
import com.vilt.kaveri.exception.RoleNotFoundException;
import com.vilt.kaveri.exception.UserDetailsNotFoundException;
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

    /** CREATE USER **/
    public PlatformUser createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already present: " + request.getEmail());
        }

        PlatformUser user = new PlatformUser();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword_hash(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNum(request.getPhoneNum());
        user.setStatus(PlatformUser.Status.ACTIVE);

        Role defaultRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Default role not found: ROLE_USER"));
        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    /** GET USER BY ID **/
    public PlatformUser getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserDetailsNotFoundException("User not found with id: " + userId));
    }

    /** GET ALL USERS **/
    public List<UserResponse> getAllUsers() {
        List<PlatformUser> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserDetailsNotFoundException("No users found in the system");
        }
        return users.stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    /** GET USER BY EMAIL **/
    public PlatformUser getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserDetailsNotFoundException("User not found with email: " + email));
    }

    /** UPDATE USER **/
    public PlatformUser updateUser(Long userId, UpdateUserRequest request) {
        PlatformUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserDetailsNotFoundException("User not found with id: " + userId));

        if (request.getUserName() != null) user.setUserName(request.getUserName());
        if (request.getPhoneNum() != null) user.setPhoneNum(request.getPhoneNum());
        if (request.getPassword() != null) user.setPassword_hash(passwordEncoder.encode(request.getPassword()));

        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            Set<Role> roles = request.getRoles().stream()
                    .map(roleName -> roleRepository.findByRoleName(roleName)
                            .orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return userRepository.save(user);
    }

    /** DELETE USER **/
    public void deleteUser(Long userId) {
        PlatformUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserDetailsNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }

    /** DTO MAPPING **/
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
