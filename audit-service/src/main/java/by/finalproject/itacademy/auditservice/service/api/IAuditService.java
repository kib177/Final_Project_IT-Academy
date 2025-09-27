package by.finalproject.itacademy.auditservice.service.api;

import by.finalproject.itacademy.auditservice.model.dto.AuditDTO;
import by.finalproject.itacademy.auditservice.model.dto.PageOfAudit;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IAuditService {

    AuditEntity convertToEntity(AuditDTO auditData);

    @Transactional(readOnly = true)
    PageOfAudit getAuditPage(Pageable pageable);

    @Transactional(readOnly = true)
    AuditDTO getAuditById(UUID uuid);
}
