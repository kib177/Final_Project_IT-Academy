package by.finalproject.itacademy.auditservice.model.dto;

import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditRequest {
    private UUID userId;
    private String userInfo;
    private EssenceTypeEnum type;
    private String essenseId;
}

