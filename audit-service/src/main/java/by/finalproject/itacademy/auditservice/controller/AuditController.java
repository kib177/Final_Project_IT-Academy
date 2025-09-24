package by.finalproject.itacademy.auditservice.controller;

import by.finalproject.itacademy.auditservice.model.dto.AuditDTO;
import by.finalproject.itacademy.auditservice.model.dto.AuditLogRequest;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.repository.AuditRepository;
import by.finalproject.itacademy.auditservice.service.api.IAuditService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditController {
    private final IAuditService auditService;
    private final AuditRepository auditRepository;


    @PostMapping("/log")
    public void logAction(@RequestBody UUID uuid) {
        auditService.createLogAction(uuid);
        ResponseEntity.status(201).build();
    }

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AuditEntity>> getAuditPage(Pageable pageable) {

        return ResponseEntity.ok(auditService.getAuditRecords(pageable));
    }

    @GetMapping("/{uuid}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<AuditEntity>> getAuditById(
            @PathVariable UUID uuid) {
        return ResponseEntity.ok(auditService.getAuditRecord(uuid));
    }
}
