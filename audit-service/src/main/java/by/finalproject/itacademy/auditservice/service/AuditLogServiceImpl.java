package by.finalproject.itacademy.auditservice.service;

import by.finalproject.itacademy.auditservice.feign.UserServiceClient;
import by.finalproject.itacademy.auditservice.model.dto.AuditRequest;
import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.model.repository.AuditRepository;
import by.finalproject.itacademy.auditservice.service.api.IAuditLogService;
import by.finalproject.itacademy.common.jwt.JwtTokenUtil;
import by.finalproject.itacademy.common.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements IAuditLogService {

    private final AuditRepository auditRepository;
    private final UserServiceClient userServiceClient;
    private final JwtTokenUtil jwtTokenUtil;

    @Transactional
    @Override
    public void createLogAction(AuditRequest auditRequest) {
        AuditEntity audit = new AuditEntity();
        audit.setDtCreate(LocalDateTime.now());

        audit.setUser(UserDTO.builder()
                .uuidUser(UUID.randomUUID())
                .mail("asdasdsd")
                .fio("asdsadsad")
                .role("USER")
                .build());

        audit.setText(auditRequest.getUserInfo());
        audit.setType(auditRequest.getType());
        audit.setEssenceId("213123213");

        auditRepository.save(audit);
    }

    public String getJwt(){
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtTokenUtil.generateToken(jwtUser.userId(), jwtUser.email(), jwtUser.fio(), jwtUser.role());
    }
}
