package by.finalproject.itacademy.auditservice.service.api;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IAuditLogService {
    @Transactional
    void createLogAction(UUID uuid);
}
