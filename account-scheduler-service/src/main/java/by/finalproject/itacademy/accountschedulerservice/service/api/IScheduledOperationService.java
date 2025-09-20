package by.finalproject.itacademy.accountschedulerservice.service.api;

import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationRequest;
import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IScheduledOperationService {
    ScheduledOperationResponse create(ScheduledOperationRequest request);

    Page<ScheduledOperationResponse> getAll(Pageable pageable, List<UUID> accountIds);

    ScheduledOperationResponse update(UUID uuid, Long dtUpdate, ScheduledOperationRequest request);
}
