package by.finalproject.itacademy.auditservice.model.dto;

import by.finalproject.itacademy.common.jwt.JwtUser;
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
    private JwtUser jwtUser;
    private String text;
    private String type;
    private String id;
}
