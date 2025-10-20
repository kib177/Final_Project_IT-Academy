package by.finalproject.itacademy.userservice.service.api;

import by.finalproject.itacademy.userservice.config.jwt.JwtUser;
import by.finalproject.itacademy.userservice.model.enums.EssenceTypeEnum;

import java.util.UUID;

public interface IAuditLogEventService {
    void sendAudit (JwtUser jwtUser, String description, UUID uuid,
                    EssenceTypeEnum essenceTypeEnum);
}
