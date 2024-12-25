package com.miladovsky.money_manager.audit;


import com.miladovsky.money_manager.domain.AuditLog;
import com.miladovsky.money_manager.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void logAction(String action, String entityId, String entityType, String details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setEntityId(entityId);
        auditLog.setEntityType(entityType);
        auditLog.setTimestamp(Instant.now());
        auditLog.setDetails(details);
        auditLogRepository.save(auditLog);
    }
}