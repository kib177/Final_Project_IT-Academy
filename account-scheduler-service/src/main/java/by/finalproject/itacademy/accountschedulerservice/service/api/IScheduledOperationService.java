package by.finalproject.itacademy.accountschedulerservice.service.api;

import by.finalproject.itacademy.accountschedulerservice.model.dto.PageOfScheduledOperation;
import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IScheduledOperationService {
    void create(ScheduledOperationRequest request);

    PageOfScheduledOperation getAll(Pageable pageable, List<UUID> accountIds);

    void update(UUID uuid, Long dtUpdate, ScheduledOperationRequest request);
}
