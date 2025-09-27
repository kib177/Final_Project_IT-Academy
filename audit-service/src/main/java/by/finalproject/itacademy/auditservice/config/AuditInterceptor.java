package by.finalproject.itacademy.auditservice.config;

import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.auditservice.repository.AuditRepository;
import by.finalproject.itacademy.common.jwt.JwtUser;
import com.sun.security.auth.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class AuditInterceptor implements HandlerInterceptor {

    private final AuditRepository auditRepository;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication.getPrincipal());
        CompletableFuture.runAsync(() -> {
            // Set the authentication in the new (async) thread's context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            saveAuditRecord(request); // Now this method has access to the SecurityContext
        });
    }

    private void saveAuditRecord(HttpServletRequest request) {

            AuditEntity audit = new AuditEntity();
            audit.setDtCreate(LocalDateTime.now());
            audit.setUser(setUserInfo());
            audit.setText(getActionDescription(request));
            audit.setType(getEntityType(request.getRequestURI()));
            audit.setEssenceId("12312312");

            auditRepository.save(audit);


    }

    private UserDTO setUserInfo() {
        try {
            JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


                return UserDTO.builder()
                        .uuidUser(jwtUser.userId())
                        .mail(jwtUser.email())
                        .fio(jwtUser.fio())
                        .role(jwtUser.role())
                        .build();

        } catch (Exception e) {
            System.err.println("Error extracting user info: " + e.getMessage());
            return createAnonymousUser();
        }
    }

    private UserDTO createAnonymousUser() {
        return UserDTO.builder()
                .uuidUser(java.util.UUID.randomUUID()) // или какой-то дефолтный UUID
                .mail("anonymous@system")
                .fio("Anonymous User")
                .role("ANONYMOUS")
                .build();
    }

    private String getActionDescription(HttpServletRequest request) {
        return switch (request.getMethod()) {
            case "POST" -> "Создание";
            case "GET" -> "Просмотр";
            case "PUT" -> "Обновление";
            case "DELETE" -> "Удаление";
            default -> "Действие";
        } + " - " + request.getRequestURI();
    }

    private EssenceTypeEnum getEntityType(String uri) {
        if (uri.contains("/users")) return EssenceTypeEnum.USER;
        if (uri.contains("/accounts")) return EssenceTypeEnum.ACCOUNT;
        if (uri.contains("/operations")) return EssenceTypeEnum.OPERATION;
        if (uri.contains("/categories")) return EssenceTypeEnum.CATEGORY;
        if (uri.contains("/currencies")) return EssenceTypeEnum.CURRENCY;
        if (uri.contains("/reports")) return EssenceTypeEnum.REPORT;
        return EssenceTypeEnum.USER;
    }

    private String extractEntityId(String uri) {
        String[] parts = uri.split("/");
        if (parts.length > 0) {
            String lastPart = parts[parts.length - 1];
            if (lastPart.matches("[0-9a-fA-F-]{36}")) {
                return lastPart;
            }
        }
        return null;
    }
}