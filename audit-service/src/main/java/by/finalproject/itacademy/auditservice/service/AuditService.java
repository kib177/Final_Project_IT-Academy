package by.finalproject.itacademy.auditservice.service;


import by.finalproject.itacademy.auditservice.client.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;
    private final UserServiceClient userServiceClient;

    public PageDTO<AuditResponseDTO> getPage(int page, int size) {
        Page<AuditEntity> auditPage = auditRepository.findAll(PageRequest.of(page, size));
        // Преобразование и возврат результата
    }

    public AuditResponseDTO get(UUID uuid) {
        AuditEntity audit = auditRepository.findById(uuid).orElseThrow();
        AuditResponseDTO response = convertToDto(audit);

        // Получение информации о пользователе через Feign Client
        response.setUser(userServiceClient.getUser(audit.getUserUuid()));

        return response;
    }

    private AuditResponseDTO convertToDto(AuditEntity entity) {
        // Преобразование entity в DTO
    }
}
