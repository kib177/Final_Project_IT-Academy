package by.finalproject.itacademy.auditservice.controller;

import by.finalproject.itacademy.auditservice.model.dto.AuditDTO;
import by.finalproject.itacademy.auditservice.model.dto.PageOfAudit;
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

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageOfAudit> getAuditPage(Pageable pageable,
            @RequestHeader UUID uuid) {

        PageOfAudit result = (PageOfAudit) auditService.getAuditPage(pageable, uuid);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{uuid}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuditDTO> getAuditById(
            @PathVariable UUID uuid) {

        AuditDTO result = auditService.getAuditById(uuid);
        return ResponseEntity.ok(result);
    }
}
