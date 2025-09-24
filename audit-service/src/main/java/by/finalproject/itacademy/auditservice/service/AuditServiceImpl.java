package by.finalproject.itacademy.auditservice.service;

import by.finalproject.itacademy.auditservice.feign.UserServiceClient;
import by.finalproject.itacademy.auditservice.model.dto.AuditDTO;
import by.finalproject.itacademy.auditservice.model.dto.AuditLogRequest;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.auditservice.repository.AuditRepository;
import by.finalproject.itacademy.auditservice.service.api.IAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AuditServiceImpl implements IAuditService  {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private UserServiceClient userServiceClient;

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

    @Override
    public AuditEntity convertToEntity(AuditDTO auditData) {
        return null;
    }

    @Override
    public Page<AuditEntity> getAuditRecords(Pageable pageable) {
        return auditRepository.findAll(pageable);
    }

    @Override
    public Optional<AuditEntity> getAuditRecord(UUID uuid) {
        return auditRepository.findById(uuid);
    }
}
