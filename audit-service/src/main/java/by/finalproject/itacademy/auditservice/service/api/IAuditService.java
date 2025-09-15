package by.finalproject.itacademy.auditservice.service.api;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IAuditService {
    @Transactional(readOnly = true)
    PageDTO<AuditResponseDTO> getPage(int page, int size);

    @Transactional(readOnly = true)
    AuditResponseDTO get(UUID uuid);

    @Transactional(readOnly = true)
    PageDTO<AuditResponseDTO> getByType(EssenceType type, int page, int size);

    @Transactional
    void createAuditRecord(UUID userUuid, String text, EssenceType type, String id);
}
