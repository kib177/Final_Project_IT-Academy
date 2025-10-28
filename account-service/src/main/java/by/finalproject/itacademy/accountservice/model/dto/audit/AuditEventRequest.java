package by.finalproject.itacademy.accountservice.model.dto.audit;

import by.finalproject.itacademy.accountservice.config.jwt.JwtUser;
import by.finalproject.itacademy.accountservice.model.enums.EssenceTypeEnum;
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