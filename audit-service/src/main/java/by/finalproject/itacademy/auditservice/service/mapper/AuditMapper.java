package by.finalproject.itacademy.auditservice.service.mapper;

import by.finalproject.itacademy.auditservice.model.dto.AuditResponse;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import org.mapstruct.Mapper;

@Mapper
public interface AuditMapper {
    AuditResponse toAuditResponse(AuditEntity auditEntity);
}
