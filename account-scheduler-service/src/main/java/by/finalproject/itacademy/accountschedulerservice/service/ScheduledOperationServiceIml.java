package by.finalproject.itacademy.accountschedulerservice.service;


import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationRequest;
import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationResponse;
import by.finalproject.itacademy.accountschedulerservice.model.entity.ScheduledOperationEntity;
import by.finalproject.itacademy.accountschedulerservice.repository.ScheduledOperationRepository;
import by.finalproject.itacademy.accountschedulerservice.service.api.IScheduledOperationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ScheduledOperationResponse create(ScheduledOperationRequest request) {
        ScheduledOperationEntity entity = modelMapper.map(request, ScheduledOperationEntity.class);

        entity.setDtCreate(Timestamp.from(Instant.now()));
        entity.setDtUpdate(entity.getDtCreate());

        ScheduledOperationEntity saved = repository.save(entity);
        return modelMapper.map(saved, ScheduledOperationResponse.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScheduledOperationResponse> getAll(Pageable pageable, List<UUID> accountIds) {
        Page<ScheduledOperationEntity> page;

        if (accountIds != null && !accountIds.isEmpty()) {
            page = repository.findByAccountIds(accountIds, pageable);
        } else {
            page = repository.findAll(pageable);
        }

        return page.map(entity -> modelMapper.map(entity, ScheduledOperationResponse.class));
    }

    @Override
    @Transactional
    public ScheduledOperationResponse update(UUID uuid, Long dtUpdate, ScheduledOperationRequest request) {
        ScheduledOperationEntity existing = repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException(String.valueOf(uuid)));

        if (!existing.getDtUpdate().equals(dtUpdate)) {
            throw new ConcurrentModificationException("Запись была изменена другим пользователем");
        }

        modelMapper.map(request, existing);
        existing.setDtUpdate(Timestamp.from(Instant.now()));

        ScheduledOperationEntity updated = repository.save(existing);
        return modelMapper.map(updated, ScheduledOperationResponse.class);
    }

    @Transactional(readOnly = true)
    public ScheduledOperationResponse getById(UUID uuid) {
        ScheduledOperationEntity entity = repository.findById(uuid)
                .orElseThrow(() -> new RuntimeException(String.valueOf(uuid)));

        return modelMapper.map(entity, ScheduledOperationResponse.class);
    }
}