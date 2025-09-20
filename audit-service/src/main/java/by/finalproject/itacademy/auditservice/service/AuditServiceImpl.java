package by.finalproject.itacademy.auditservice.service;

import by.finalproject.itacademy.auditservice.feign.TestFeign;
import by.finalproject.itacademy.auditservice.model.dto.AuditDTO;
import by.finalproject.itacademy.auditservice.model.dto.PageOfAudit;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.repository.AuditRepository;
import by.finalproject.itacademy.auditservice.service.api.IAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements IAuditService {
    private final AuditRepository auditRepository;
    private final TestFeign tesst;

    @Transactional(readOnly = true)
    @Override
    public PageOfAudit getAuditPage(Pageable pageable, UUID uuid) {
        Page<AuditEntity> auditPage = auditRepository.findAll(pageable);

        List<AuditDTO> content = auditPage.getContent().stream()
                .map(audit -> convertToResponse(audit, uuid))
                .collect(Collectors.toList());

        PageOfAudit response = new PageOfAudit();
        response.setNumber(auditPage.getNumber());
        response.setSize(auditPage.getSize());
        response.setTotalPages(auditPage.getTotalPages());
        response.setTotalElements(auditPage.getTotalElements());
        response.setFirst(auditPage.isFirst());
        response.setNumberOfElements(auditPage.getNumberOfElements());
        response.setLast(auditPage.isLast());
        response.setContent(content);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public AuditDTO getAuditById(UUID uuid) {
        AuditEntity audit = tesst.getUser(uuid)
                .orElseThrow(() -> new RuntimeException("хуила " + String.valueOf(uuid)));

        return convertToResponse(audit, uuid);
    }

    private AuditDTO convertToResponse(AuditEntity audit, UUID uuid) {
        AuditDTO response = new AuditDTO();
        response.setUuid(audit.getUuid());
        response.setDtCreate(audit.getDtCreate());
        response.setText(audit.getText());
        response.setType(audit.getType().toString());
        response.setId(audit.getEssenceId());

        response.setUser();

        return response;
    }
}
