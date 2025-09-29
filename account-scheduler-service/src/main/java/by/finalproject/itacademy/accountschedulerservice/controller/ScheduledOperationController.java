package by.finalproject.itacademy.accountschedulerservice.controller;

import by.finalproject.itacademy.accountschedulerservice.model.dto.PageOfScheduledOperation;
import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationRequest;
import by.finalproject.itacademy.accountschedulerservice.service.api.IScheduledOperationService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> create(
            @RequestBody ScheduledOperationRequest request) {
        service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public PageOfScheduledOperation getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) List<UUID> uuid) {

        return ResponseEntity.status(HttpStatus.OK).body(service.getAll(
                PageRequest.of(page, size), uuid)).getBody();
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> update(
            @PathVariable UUID uuid,
            @PathVariable("dt_update") Long dtUpdate,
            @RequestBody ScheduledOperationRequest request) {

        service.update(uuid, dtUpdate, request);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}