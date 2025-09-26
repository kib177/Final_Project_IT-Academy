package by.finalproject.itacademy.auditservice.controller;

import by.finalproject.itacademy.auditservice.model.dto.AuditDTO;
import by.finalproject.itacademy.auditservice.model.dto.AuditLogRequest;
import by.finalproject.itacademy.auditservice.model.dto.PageOfAudit;
import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.repository.AuditRepository;
import by.finalproject.itacademy.auditservice.service.api.IAuditLogService;
import by.finalproject.itacademy.auditservice.service.api.IAuditService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditController {
    private final IAuditService auditService;
    private final IAuditLogService auditLogService;
    private final AuditRepository auditRepository;
    private Authentication authentication;


    @PostMapping("/log")
    public void logAction(@RequestBody UserDTO userDTO) {
    userDTO.setFio(String.valueOf(SecurityContextHolder.getContext().getAuthentication()
            .getName()));
        auditLogService.createLogAction(userDTO);
        ResponseEntity.status(201).build();
    }

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageOfAudit> getAuditPage(Pageable pageable) {

        return ResponseEntity.ok(auditService.getAuditPage(pageable));
    }

    @GetMapping("/{uuid}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAuditById(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok().build();
    }
}
