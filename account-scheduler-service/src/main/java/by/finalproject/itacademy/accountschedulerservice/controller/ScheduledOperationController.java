package by.finalproject.itacademy.accountschedulerservice.controller;

import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationRequest;
import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationResponse;
import by.finalproject.itacademy.accountschedulerservice.service.api.IScheduledOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/scheduler/operation")
@RequiredArgsConstructor
public class ScheduledOperationController {
    private final IScheduledOperationService service;

    @PostMapping
    public ResponseEntity<ScheduledOperationResponse> create(
            @RequestBody ScheduledOperationRequest request) {

        ScheduledOperationResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ScheduledOperationResponse>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) List<UUID> uuid) {

        Page<ScheduledOperationResponse> response = service.getAll(
                PageRequest.of(page, size), uuid);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<ScheduledOperationResponse> update(
            @PathVariable UUID uuid,
            @PathVariable("dt_update") Long dtUpdate,
            @RequestBody ScheduledOperationRequest request) {

        ScheduledOperationResponse response = service.update(uuid, dtUpdate, request);
        return ResponseEntity.ok(response);
    }
}