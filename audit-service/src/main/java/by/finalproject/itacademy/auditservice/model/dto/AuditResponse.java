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
public class AuditResponse {
    private UUID uuid;
    private LocalDateTime dtCreate;
    private UserDTO user;
    private String text;
    private EssenceTypeEnum type;
    private UUID essenceId;
}

