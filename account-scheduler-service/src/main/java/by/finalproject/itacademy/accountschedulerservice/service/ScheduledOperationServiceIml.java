package by.finalproject.itacademy.accountschedulerservice.service;

import by.finalproject.itacademy.accountschedulerservice.model.dto.PageOfScheduledOperation;
import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationRequest;
import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationResponse;
import by.finalproject.itacademy.accountschedulerservice.model.entity.ScheduledOperationEntity;
import by.finalproject.itacademy.accountschedulerservice.repository.ScheduledOperationRepository;
import by.finalproject.itacademy.accountschedulerservice.service.api.IScheduledOperationService;
import by.finalproject.itacademy.accountschedulerservice.service.mapper.PageMapper;
import by.finalproject.itacademy.accountschedulerservice.service.mapper.ScheduledMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduledOperationServiceIml implements IScheduledOperationService {
    private final ScheduledOperationRepository repository;
    private final ScheduledMapper mapper;
    private final PageMapper pageMapper;

    @Override
    @Transactional
    public void create(ScheduledOperationRequest request) {

        repository.save(ScheduledOperationEntity.builder()
                .dtCreate(Timestamp.from(Instant.now()))
                .dtUpdate(Timestamp.from(Instant.now()))
                .schedule(request.getSchedule())
                .operation(request.getOperation())
                .build());
    }

    @Override
    @Transactional(readOnly = true)
    public PageOfScheduledOperation getAll(Pageable pageable, List<UUID> accountIds) {
        Page<ScheduledOperationEntity> page = repository.findAllByUuid(pageable, accountIds);

        return pageMapper.toPageOfUser(page, mapper);
    }

    @Override
    @Transactional
    public void update(UUID uuid, Long dtUpdate, ScheduledOperationRequest request) {
        ScheduledOperationEntity existing = repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException(String.valueOf(uuid)));

        if (!existing.getDtUpdate().equals(dtUpdate)) {
            throw new ConcurrentModificationException("Запись была изменена другим пользователем");
        }

        ScheduledOperationEntity updated = repository.save(ScheduledOperationEntity.builder()
                .dtCreate(Timestamp.from(Instant.now()))
                .dtUpdate(Timestamp.from(Instant.now()))
                .schedule(request.getSchedule())
                .operation(request.getOperation())
                .build());

    }

    @Transactional(readOnly = true)
    public ScheduledOperationResponse getById(UUID uuid) {
        ScheduledOperationEntity entity = repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException(String.valueOf(uuid)));

        return mapper.toDto(entity);
    }
}