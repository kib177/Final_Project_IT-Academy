package by.finalproject.itacademy.auditservice.controller;

import by.finalproject.itacademy.auditservice.model.dto.PageOfAudit;
import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import by.finalproject.itacademy.auditservice.service.api.IAuditLogService;
import by.finalproject.itacademy.auditservice.service.api.IAuditService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditController {
    private final IAuditService auditService;
    private final IAuditLogService auditLogService;

    @PostMapping("/log")
    public void logAction(@RequestBody UserDTO userDTO) {
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
        return ResponseEntity.ok(auditService.getAuditById(uuid));
    }
}
