package com.vilt.kaveri.aop;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.vilt.kaveri.entity.AuditLog;
import com.vilt.kaveri.service.AuditLogService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {

    private final AuditLogService auditLogService;

    @AfterReturning("@annotation(loggableAction)")
    public void logAction(JoinPoint joinPoint, LoggableAction loggableAction) {
        try {
            Object[] args = joinPoint.getArgs();

            Long adminId = null;
            if (args.length > 0 && args[0] instanceof Long) {
                adminId = (Long) args[0];
            }

            AuditLog logEntry = AuditLog.builder()
                    .adminId(adminId)
                    .action(loggableAction.action())
                    .entity(loggableAction.entity())
                    .details("Executed method: " + joinPoint.getSignature().getName())
                    .actionTime(LocalDateTime.now())
                    .build();

            auditLogService.saveLog(logEntry);
            log.info("Audit Log saved: {}", logEntry);

        } catch (Exception e) {
            log.error("Failed to save audit log: {}", e.getMessage());
        }
    }
}
