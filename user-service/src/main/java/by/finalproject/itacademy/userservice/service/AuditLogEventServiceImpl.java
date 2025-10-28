package by.finalproject.itacademy.userservice.service;


import by.finalproject.itacademy.userservice.config.jwt.JwtUser;
import by.finalproject.itacademy.userservice.feign.AuditServiceClient;
import by.finalproject.itacademy.userservice.model.dto.audit.AuditEventRequest;
import by.finalproject.itacademy.userservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.userservice.service.api.IAuditLogEventService;
import by.finalproject.itacademy.userservice.service.exception.FeignClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogEventServiceImpl implements IAuditLogEventService {
    private final AuditServiceClient auditServiceClient;

    @Override
    public void sendAudit(JwtUser jwtUser, String description, UUID uuid,
                          EssenceTypeEnum essenceTypeEnum) {
        try{
            auditServiceClient.logEvent(
                    AuditEventRequest.builder()
                            .jwtUser(jwtUser)
                            .userInfo(description)
                            .essenceId(uuid)
                            .type(essenceTypeEnum)
                            .build());
            log.info("Audit event sent{}", essenceTypeEnum);
        } catch (FeignClientException e) {
            throw new FeignClientException("Audit event not sent", e);
        }

    }
}
