package by.finalproject.itacademy.auditservice.model.dto;

import by.finalproject.itacademy.common.dto.PageDTO;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageOfAudit extends PageDTO<AuditDTO> {
}
