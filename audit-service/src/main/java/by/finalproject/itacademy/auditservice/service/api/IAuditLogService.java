package by.finalproject.itacademy.auditservice.service.api;

import by.finalproject.itacademy.auditservice.model.dto.AuditRequest;
import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IAuditLogService {
    @Transactional
    void createLogAction(AuditRequest request);
}
