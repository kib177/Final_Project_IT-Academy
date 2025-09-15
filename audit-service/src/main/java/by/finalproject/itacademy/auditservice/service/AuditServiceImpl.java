package by.finalproject.itacademy.auditservice.service;

import by.finalproject.itacademy.auditservice.client.UserServiceClient;
import by.finalproject.itacademy.auditservice.model.EssenceTypeEnum;
import by.finalproject.itacademy.auditservice.service.api.IAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements IAuditService {
    private final AuditRepository auditRepository;
    private final UserServiceClient userServiceClient;

    @Transactional(readOnly = true)
    @Override
    public PageDTO<AuditResponseDTO> getPage(int page, int size) {
        Page<AuditEntity> auditPage = auditRepository.findAll(
                PageRequest.of(page, size, Sort.by("dtCreate").descending()));

        return convertToPageDTO(auditPage);
    }

    @Transactional(readOnly = true)
    @Override
    public AuditResponseDTO get(UUID uuid) {
        AuditEntity audit = auditRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Audit record not found"));
        return convertToDTO(audit);
    }

    @Transactional(readOnly = true)
    @Override
    public PageDTO<AuditResponseDTO> getByType(EssenceTypeEnum type, int page, int size) {
        Page<AuditEntity> auditPage = auditRepository.findByType(
                type, PageRequest.of(page, size, Sort.by("dtCreate").descending()));

        return convertToPageDTO(auditPage);
    }

    @Transactional
    @Override
    public void createAuditRecord(UUID userUuid, String text, EssenceTypeEnum type, String id) {
        AuditEntity audit = new AuditEntity();
        audit.setUserUuid(userUuid);
        audit.setText(text);
        audit.setType(type);
        audit.setId(id);
        audit.setDtCreate(Instant.now().getEpochSecond());

        auditRepository.save(audit);
    }

    private AuditResponseDTO convertToDTO(AuditEntity audit) {
        AuditResponseDTO dto = new AuditResponseDTO();
        dto.setUuid(audit.getUuid());
        dto.setDtCreate(audit.getDtCreate());

        // Get user info via Feign client
        try {
            UserResponseDTO user = userServiceClient.getUser(audit.getUserUuid());
            dto.setUser(user);
        } catch (Exception e) {
            // Log error but don't fail the request
            dto.setUser(null);
        }

        dto.setText(audit.getText());
        dto.setType(audit.getType());
        dto.setId(audit.getId());
        return dto;
    }

    private PageDTO<AuditResponseDTO> convertToPageDTO(Page<AuditEnt    ity> page) {
        PageDTO<AuditResponseDTO> pageDTO = new PageDTO<>();
        pageDTO.setNumber(page.getNumber());
        pageDTO.setSize(page.getSize());
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements(page.getTotalElements());
        pageDTO.setFirst(page.isFirst());
        pageDTO.setNumberOfElements(page.getNumberOfElements());
        pageDTO.setLast(page.isLast());
        pageDTO.setContent(page.map(this::convertToDTO).getContent());
        return pageDTO;
    }
}
