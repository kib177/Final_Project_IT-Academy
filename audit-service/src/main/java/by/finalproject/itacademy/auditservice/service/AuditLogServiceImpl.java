package by.finalproject.itacademy.auditservice.service;

import by.finalproject.itacademy.auditservice.feign.UserServiceClient;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.auditservice.repository.AuditRepository;
import by.finalproject.itacademy.auditservice.service.api.IAuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements IAuditLogService {

    private final AuditRepository auditRepository;
    private final UserServiceClient userServiceClient;

    /*@Override
    public void createLogAction(AuditLogRequest request) {
    }*/

    @Transactional
    @Override
    public void createLogAction(UUID uuid) {
        AuditEntity audit = new AuditEntity();
        audit.setDtCreate(LocalDateTime.now());

        audit.setUserUuid(String.valueOf(uuid));

        audit.setText("request.getText()");
        audit.setType(EssenceTypeEnum.USER);
        audit.setEssenceId(uuid.toString());

        auditRepository.save(audit);
    }
}
