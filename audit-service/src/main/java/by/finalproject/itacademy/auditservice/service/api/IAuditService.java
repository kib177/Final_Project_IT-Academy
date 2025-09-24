package by.finalproject.itacademy.auditservice.service.api;

import by.finalproject.itacademy.auditservice.model.dto.AuditDTO;
import by.finalproject.itacademy.auditservice.model.dto.AuditLogRequest;
import by.finalproject.itacademy.auditservice.model.dto.PageOfAudit;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;


import java.util.Optional;
import java.util.UUID;

public interface IAuditService {

  /*  void createLogAction(AuditLogRequest request);*/

    void createLogAction(UUID uuid);

    AuditEntity convertToEntity(AuditDTO auditData);

    Page<AuditEntity> getAuditRecords(Pageable pageable);

    Optional<AuditEntity> getAuditRecord(UUID uuid);
}
