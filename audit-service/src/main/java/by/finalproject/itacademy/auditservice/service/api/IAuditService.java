package by.finalproject.itacademy.auditservice.service.api;

import by.finalproject.itacademy.auditservice.model.dto.AuditDTO;
import by.finalproject.itacademy.auditservice.model.dto.PageOfAudit;
import org.springframework.data.domain.Pageable;


import java.util.UUID;

public interface IAuditService {
    AuditDTO getAuditById(UUID uuid);
    PageOfAudit getAuditPage(Pageable pageable, UUID uuid);
}
