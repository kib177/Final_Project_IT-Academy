package by.finalproject.itacademy.accountservice.controller;

import by.finalproject.itacademy.accountservice.model.dto.OperationDTO;
import by.finalproject.itacademy.accountservice.model.entity.OperationEntity;
import by.finalproject.itacademy.accountservice.service.api.IOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account/{accountUuid}/operation")
public class OperationController {
    private final IOperationService operationService;

    @PostMapping
    public ResponseEntity<OperationDTO> createOperation(
            @PathVariable UUID accountUuid,
            @RequestBody OperationDTO operation) {
        // Установка счета для операции
        OperationDTO createdOperation = operationService.createOperation(operation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOperation);
    }

    @GetMapping
    public ResponseEntity<Page<OperationDTO>> getOperations(
            @PathVariable UUID accountUuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OperationDTO> operations = operationService.getOperationsByAccount(accountUuid, pageable);
        return ResponseEntity.ok(operations);
    }

    @PutMapping("/{operationUuid}/dt_update/{dt_update}")
    public ResponseEntity<OperationDTO> updateOperation(
            @PathVariable UUID accountUuid,
            @PathVariable UUID operationUuid,
            @PathVariable("dt_update") Long dtUpdate,
            @RequestBody OperationDTO operation) {
        // Проверка версии (dtUpdate) должна быть реализована
        operation.setUuid(operationUuid);
        OperationDTO updatedOperation = operationService.updateOperation(operation);
        return ResponseEntity.ok(updatedOperation);
    }

    @DeleteMapping("/{operationUuid}/dt_update/{dt_update}")
    public ResponseEntity<Void> deleteOperation(
            @PathVariable UUID accountUuid,
            @PathVariable UUID operationUuid,
            @PathVariable("dt_update") Long dtUpdate) {
        // Проверка версии (dtUpdate) должна быть реализована
        operationService.deleteOperation(operationUuid);
        return ResponseEntity.ok().build();
    }
}
