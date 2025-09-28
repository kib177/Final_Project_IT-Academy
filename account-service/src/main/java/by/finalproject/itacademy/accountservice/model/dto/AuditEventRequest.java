package by.finalproject.itacademy.accountservice.model.dto;

import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditEventRequest {
    private UUID userUuid;
    private String userInfo;
    private EssenceTypeEnum type;
    private String essenceId;
}