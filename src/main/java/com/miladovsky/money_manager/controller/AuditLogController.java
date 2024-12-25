package com.miladovsky.money_manager.controller;

import com.miladovsky.money_manager.domain.AuditLog;
import com.miladovsky.money_manager.repository.AuditLogRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;

    public AuditLogController(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @GetMapping
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }
}