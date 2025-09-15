package by.finalproject.itacademy.auditservice.service.api;

import by.finalproject.itacademy.auditservice.model.dto.AuditDTO;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.common.dto.PageDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IAuditService {
    @Transactional(readOnly = true)
    PageDTO<AuditDTO> getPage(int page, int size);

    @Transactional(readOnly = true)
    AuditDTO get(UUID uuid);

    @Transactional(readOnly = true)
    PageDTO<AuditDTO> getByType(EssenceTypeEnum type, int page, int size);

    @Transactional
    void createAuditRecord(UUID userUuid, String text, EssenceTypeEnum type, String id);
}
