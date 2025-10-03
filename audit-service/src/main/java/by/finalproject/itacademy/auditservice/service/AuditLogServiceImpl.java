package by.finalproject.itacademy.auditservice.service;

import by.finalproject.itacademy.auditservice.model.dto.AuditRequest;
import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.repository.AuditRepository;
import by.finalproject.itacademy.auditservice.service.api.IAuditLogService;
import by.finalproject.itacademy.common.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements IAuditLogService {

    private final AuditRepository auditRepository;

    @Transactional
    @Override
    public void createLogAction(AuditRequest auditRequest) {
        AuditEntity audit = new AuditEntity();
        JwtUser jwtUser = auditRequest.getJwtUser();
        audit.setDtCreate(LocalDateTime.now());

        audit.setUser(UserDTO.builder()
                .uuidUser(jwtUser.userId())
                .mail(jwtUser.email())
                .fio(jwtUser.fio())
                .role(jwtUser.role())
                .build());

        audit.setText(auditRequest.getUserInfo());
        audit.setType(auditRequest.getType());
        audit.setEssenceId(auditRequest.getEssenseId());

        auditRepository.save(audit);
    }
}
