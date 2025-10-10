package by.finalproject.itacademy.auditservice.service.api;

import by.finalproject.itacademy.auditservice.model.dto.AuditResponse;
import by.finalproject.itacademy.auditservice.model.dto.PageOfAudit;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IAuditService {
    @Transactional(readOnly = true)
    PageOfAudit getAuditPage(Pageable pageable);

    @Transactional(readOnly = true)
    AuditResponse getAuditById(UUID uuid);
}
