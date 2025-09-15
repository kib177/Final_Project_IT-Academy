package by.finalproject.itacademy.auditservice.service;

import by.finalproject.itacademy.auditservice.feign.UserServiceClient;
import by.finalproject.itacademy.auditservice.model.dto.AuditDTO;
import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.auditservice.repository.AuditRepository;
import by.finalproject.itacademy.auditservice.service.api.IAuditService;
import by.finalproject.itacademy.common.dto.PageDTO;
import by.finalproject.itacademy.userservice.model.dto.User;
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
    public PageDTO<AuditDTO> getPage(int page, int size) {
        Page<AuditEntity> auditPage = auditRepository.findAll(
                PageRequest.of(page, size, Sort.by("dtCreate").descending()));

        return convertToPageDTO(auditPage);
    }

    @Transactional(readOnly = true)
    @Override
    public AuditDTO get(UUID uuid) {
        AuditEntity audit = auditRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Audit record not found"));
        return convertToDTO(audit);
    }

    @Transactional(readOnly = true)
    @Override
    public PageDTO<AuditDTO> getByType(EssenceTypeEnum type, int page, int size) {
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

    private AuditDTO convertToDTO(AuditEntity audit) {
        AuditDTO dto = new AuditDTO();
        dto.setUuid(audit.getUuid());
        dto.setDtCreate(audit.getDtCreate());

        try {
            User user = userServiceClient.getUser(audit.getUserUuid());
            UserDTO userDto = new UserDTO();
            userDto.setUuid(user.getUuid());
            userDto.setMail(user.getMail());
            userDto.setFio(user.getFio());
            userDto.setRole(user.getRole().name());
            dto.setUser(userDto);
        } catch (Exception e) {
            dto.setUser(null);
        }

        dto.setText(audit.getText());
        dto.setType(audit.getType().name());
        dto.setId(audit.getId());
        return dto;
    }

    private PageDTO<AuditDTO> convertToPageDTO(Page<AuditEntity> page) {
        PageDTO<AuditDTO> pageDTO = new PageDTO<>();
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
