package com.vilt.kaveri.repository;

import com.vilt.kaveri.model.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<PlatformUser, Long> {
    Optional<PlatformUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
