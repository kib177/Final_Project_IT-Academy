package by.finalproject.itacademy.accountservice.service.api;

import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.common.jwt.JwtUser;

import java.util.UUID;

public interface IAuditLogEventService {
    void sendAudit (JwtUser jwtUser, String description, UUID uuid,
                    EssenceTypeEnum essenceTypeEnum);
}
