package by.finalproject.itacademy.auditservice.service;

import by.finalproject.itacademy.auditservice.model.dto.AuditResponse;
import by.finalproject.itacademy.auditservice.model.dto.PageOfAudit;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.repository.AuditRepository;
import by.finalproject.itacademy.auditservice.service.api.IAuditService;
import by.finalproject.itacademy.auditservice.service.exception.AuditNotFoundException;
import by.finalproject.itacademy.auditservice.service.mapper.AuditMapper;
import by.finalproject.itacademy.auditservice.service.mapper.PageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements IAuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;
    private final PageMapper pageMapper;

    @Transactional(readOnly = true)
    @Override
    public PageOfAudit getAuditPage(Pageable pageable) {
        Page<AuditEntity> auditPage = auditRepository.findAll(pageable);
        return pageMapper.toPageOfUser(auditPage, auditMapper);
    }

    @Transactional(readOnly = true)
    @Override
    public AuditResponse getAuditById(UUID uuid) {
        AuditEntity auditEntity = auditRepository.findAuditEntityByUuid(uuid)
                .orElseThrow(() -> new AuditNotFoundException("Запись не найдена " + uuid));
        return auditMapper.toAuditResponse(auditEntity);
    }
}
