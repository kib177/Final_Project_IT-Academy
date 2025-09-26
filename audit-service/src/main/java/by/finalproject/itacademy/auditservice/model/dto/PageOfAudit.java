package by.finalproject.itacademy.auditservice.model.dto;

import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.common.model.dto.PageDTO;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageOfAudit extends PageDTO<AuditEntity> {
}
