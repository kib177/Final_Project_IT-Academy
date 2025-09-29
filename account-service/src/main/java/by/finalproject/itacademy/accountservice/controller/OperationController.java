package by.finalproject.itacademy.accountservice.controller;

import by.finalproject.itacademy.accountservice.model.dto.OperationRequest;
import by.finalproject.itacademy.accountservice.model.dto.PageOfOperation;
import by.finalproject.itacademy.accountservice.service.api.IOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class OperationController {
    private final IOperationService operationService;

    @PostMapping("/{uuid}/operation")
    public ResponseEntity<?> createOperation(
            @PathVariable UUID uuid,
            @RequestBody OperationRequest operation) {
        try {
            operationService.createOperation(uuid, operation);
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{uuid}/operation")
    public ResponseEntity<PageOfOperation> getOperations(
            @PathVariable UUID uuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageOfOperation operations = null;
        try {
            operations = operationService.getAccountOperations(uuid, PageRequest.of(page, size));
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(operations);
    }

    @PutMapping("/{uuid}/operation/{operationUuid}/dt_update/{dt_update}")
    public ResponseEntity<?> updateOperation(
            @PathVariable UUID uuid,
            @PathVariable UUID operationUuid,
            @PathVariable("dt_update") LocalDateTime dtUpdate,
            @RequestBody OperationRequest operation) {

        try {
            operationService.updateOperation(uuid, operationUuid, dtUpdate,operation);
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{uuid}/operation/{operationUuid}/dt_update/{dt_update}")
    public ResponseEntity<Void> deleteOperation(
            @PathVariable UUID uuid,
            @PathVariable UUID operationUuid,
            @PathVariable("dt_update") LocalDateTime dtUpdate) {
        try {
            operationService.deleteOperation(uuid, operationUuid, dtUpdate);
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }
}
