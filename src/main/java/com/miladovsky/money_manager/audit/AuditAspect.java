package com.miladovsky.money_manager.audit;

import com.miladovsky.money_manager.domain.AuditLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Aspect
@Component
public class AuditAspect {

    private final AuditService auditService;

    public AuditAspect(AuditService auditService) {
        this.auditService = auditService;
    }

    @Around("@annotation(com.miladovsky.money_manager.audit.Auditable)")
    public Object aroundAuditableMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        // Proceed with the original method call
        Object result = joinPoint.proceed();

        // Get the annotation data
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Auditable auditable = signature.getMethod().getAnnotation(Auditable.class);
        String action = auditable.action();
        String entityType = auditable.entityType();
        String customDetail = auditable.detail();

        // Extract arguments or return value for additional info
        Object[] args = joinPoint.getArgs();

        // Attempt to determine entityId from either the arguments or the result.
        // This part is domain-specific. You might parse 'id' from the result if it's an entity.
        String entityId = extractEntityId(args, result);

        // (Optional) retrieve the current user from SecurityContext if using Spring Security
        String username = getCurrentUsername();

        // Create the AuditLog object
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setTimestamp(Instant.now());
        log.setUsername(username);
        log.setDetails(customDetail);

        // Save log
        auditService.logAction(log);

        return result;
    }

    private String getCurrentUsername() {
        // If using Spring Security:
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // return auth != null ? auth.getName() : "anonymous";
        return "SYSTEM"; // Fallback or "anonymous" if no security is in place
    }

    private String extractEntityId(Object[] args, Object result) {
        // Implementation depends on your domain model.
        // Possibly check if result or one of the args implements an interface like Identifiable
        // For example:
        // if (result instanceof Income) {
        //     return ((Income) result).getId();
        // }
        return null;
    }
}