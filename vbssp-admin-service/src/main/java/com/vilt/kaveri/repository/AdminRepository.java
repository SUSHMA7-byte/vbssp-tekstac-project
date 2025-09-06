package com.vilt.kaveri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vilt.kaveri.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
