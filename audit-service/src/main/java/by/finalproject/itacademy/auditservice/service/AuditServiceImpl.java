package by.finalproject.itacademy.auditservice.service;

import by.finalproject.itacademy.auditservice.model.dto.AuditDTO;
import by.finalproject.itacademy.auditservice.model.dto.PageOfAudit;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.model.repository.AuditRepository;
import by.finalproject.itacademy.auditservice.service.api.IAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AuditServiceImpl implements IAuditService  {

    @Autowired
    private AuditRepository auditRepository;

    /*@Override
    public void createLogAction(AuditLogRequest request) {
    }*/

    @Override
    public AuditEntity convertToEntity(AuditDTO auditData) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public PageOfAudit getAuditPage(Pageable pageable) {
        Page<AuditEntity> auditPage = auditRepository.findAll(pageable);
        List<AuditEntity> content = new ArrayList<>(auditPage.getContent());

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

   @Transactional(readOnly = true)
   @Override
   public AuditDTO getAuditById(UUID uuid) {
        AuditEntity auditEntity = auditRepository.findAuditEntityByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("хуила " + String.valueOf(uuid)));
        return convertToResponse(auditEntity);
    }

    private AuditDTO convertToResponse(AuditEntity audit) {
        AuditDTO response = new AuditDTO();
        response.setUuid(audit.getUuid());
        response.setDtCreate(audit.getDtCreate());
        response.setText(audit.getText());
        response.setType(audit.getType());
        response.setId(audit.getEssenceId());
        response.setUser(audit.getUser());
        return response;
    }
}
