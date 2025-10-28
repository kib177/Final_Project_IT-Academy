package by.finalproject.itacademy.accountservice.service.api;

import by.finalproject.itacademy.accountservice.model.dto.OperationRequest;
import by.finalproject.itacademy.accountservice.model.dto.PageOfOperation;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

public interface IOperationService {
    @Transactional
    void createOperation(UUID accountUuid, OperationRequest operationRequest);

    PageOfOperation getAccountOperations(UUID accountUuid, Pageable pageable);

    @Transactional
    void updateOperation(UUID accountUuid, UUID operationUuid, LocalDateTime dtUpdate,
                         OperationRequest operationRequest);

    @Transactional
    void deleteOperation(UUID accountUuid, UUID operationUuid, LocalDateTime dtUpdate);
}
