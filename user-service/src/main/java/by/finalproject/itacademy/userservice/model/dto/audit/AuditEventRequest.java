package by.finalproject.itacademy.userservice.model.dto.audit;

import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.common.jwt.JwtUser;
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
    private JwtUser jwtUser;
    private String userInfo;
    private EssenceTypeEnum type;
    private UUID essenceId;
}