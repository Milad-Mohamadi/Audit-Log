package com.miladovsky.money_manager.repository;


import com.miladovsky.money_manager.domain.AuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditLogRepository extends MongoRepository<AuditLog, String> {

}