package com.vilt.kaveri.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vilt.kaveri.entity.AuditLog;
import com.vilt.kaveri.repository.AuditLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    public List<AuditLog> getLogsByAdmin(Long adminId) {
        return auditLogRepository.findByAdminId(adminId);
    }

    public AuditLog saveLog(AuditLog log) {
        return auditLogRepository.save(log);
    }
}
