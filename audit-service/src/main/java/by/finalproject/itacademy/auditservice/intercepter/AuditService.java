package by.finalproject.itacademy.auditservice.intercepter;

import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.auditservice.repository.AuditRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

   /* @Autowired
    private UserServiceClient userServiceClient;*/

    @Async("auditTaskExecutor")
    public void saveAuditAsync(AuditContext context) {
        try {
            saveAuditFromContext(context);
        } catch (Exception e) {
            System.out.println("Async audit save failed: {}" + e.getMessage() + e);
        }
    }

    @Transactional
    public void saveAuditFromContext(AuditContext context) {
        try {
            AuditEntity auditEntity = new AuditEntity();

            // Устанавливаем основные поля
            auditEntity.setDtCreate(LocalDateTime.now());

            // Определяем тип сущности из URI
           // String essenceType = determineEssenceType(context.getUri());
            auditEntity.setType(EssenceTypeEnum.ACCOUNT);

            // Извлекаем ID сущности
            //String essenceId = extractEssenceId(context.getUri(), context.getMethod());
            auditEntity.setEssenceId(context.getUri());

            // Формируем текст действия
            String actionText = buildActionText(context);
            auditEntity.setText(actionText);

            // Создаем или получаем информацию о пользователе
            UUID uuid= context.getUserUuid();
            auditEntity.setUserUuid(String.valueOf(uuid));

            // СОХРАНЯЕМ в базу
            AuditEntity savedEntity = auditRepository.save(auditEntity);
            System.out.println("Audit saved successfully with ID: {}" + savedEntity.getUuid());

        } catch (Exception e) {
            System.out.println("Failed to save audit record: {}" + e.getMessage() + e);
            throw new RuntimeException("Audit save failed", e);
        }
    }

   /* private UserEmbedded createUserEmbedded(AuditContext context) {
        UserEmbedded user = new UserEmbedded();

        if (context.getUserUuid() != null) {
            user.setUuid();
            user.setRole(UserRole.valueOf(context.getUserRole()));

            // Пытаемся получить детальную информацию о пользователе
            try {
                User detailedUser = userServiceClient.getUser(context.getUserUuid());
                user.setMail(detailedUser.getMail());
                user.setFio(detailedUser.getFio());
            } catch (Exception e) {
                log.warn("Could not fetch user details for UUID: {}, using basic info", context.getUserUuid());
                user.setMail("unknown@user");
                user.setFio("Unknown User");
            }
        } else {
            // Анонимный пользователь
            user.setUuid(UUID.randomUUID());
            user.setMail("anonymous@system");
            user.setFio("Anonymous User");
            user.setRole(UserRole.USER);
        }

        return user;
    }*/

   /* private String determineEssenceType(String uri) {
        if (uri.contains("/users")) return "USER";
        if (uri.contains("/reports")) return "REPORT";
        if (uri.contains("/currency")) return "CURRENCY";
        if (uri.contains("/categories")) return "CATEGORY";
        if (uri.contains("/accounts")) return "ACCOUNT";
        if (uri.contains("/operations")) return "OPERATION";
        return "SYSTEM";
    }*/

   /* private String extractEssenceId(String uri, String method) {
        // Для POST запросов - создание новой сущности
        if ("POST".equals(method)) {
            return "NEW";
        }

        // Извлекаем UUID из пути типа /api/v1/users/{uuid}
        try {
            String[] pathSegments = uri.split("/");
            for (int i = 0; i < pathSegments.length; i++) {
                String segment = pathSegments[i];
                if (isUuid(segment) && i > 0) {
                    // Проверяем, что предыдущий сегмент - это название сущности
                    String previousSegment = pathSegments[i-1];
                    if (previousSegment.matches("users|reports|currency|categories|accounts|operations")) {
                        return segment;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Could not extract essence ID from URI: {}" + uri);
        }

        return "UNKNOWN";
    }*/

    private boolean isUuid(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private String buildActionText(AuditContext context) {
        StringBuilder sb = new StringBuilder();

        if (context.getUserUuid() != null) {
            sb.append("User ").append(context.getUserUuid());
        } else {
            sb.append("Anonymous user");
        }

        sb.append(" executed ").append(context.getMethod())
                .append(" request to '").append(context.getUri()).append("'");

        if (context.getQueryString() != null) {
            sb.append(" with query: ").append(context.getQueryString());
        }

        sb.append(". Status: ").append(context.getResponseStatus())
                .append(". Duration: ").append(context.getDuration()).append("ms");

        if (context.getError() != null) {
            sb.append(". Error: ").append(context.getError());
        }

        if (context.getClientIp() != null) {
            sb.append(". IP: ").append(context.getClientIp());
        }

        return sb.toString();
    }


      public Page<AuditEntity> getAuditRecords(Pageable pageable) {
        return auditRepository.findAll(pageable);
    }

    public Optional<AuditEntity> getAuditRecord(UUID uuid) {
        return auditRepository.findById(uuid);
    }
}