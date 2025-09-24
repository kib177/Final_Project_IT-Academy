package by.finalproject.itacademy.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditLogRequest {
    private UUID userUuid;
    private String text;
    private String type;
    private String id;
}
