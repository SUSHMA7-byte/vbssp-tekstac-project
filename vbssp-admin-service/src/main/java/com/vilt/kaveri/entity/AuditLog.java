package com.vilt.kaveri.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    private Long adminId;
    private String action;
    private String entity;
    private Long entityId;
    private String details;

    private LocalDateTime actionTime = LocalDateTime.now();
}
