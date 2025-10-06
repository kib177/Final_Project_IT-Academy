package by.finalproject.itacademy.accountservice.service;

import by.finalproject.itacademy.accountservice.feign.AuditServiceClient;
import by.finalproject.itacademy.accountservice.feign.ClassifierCerviceClient;
import by.finalproject.itacademy.accountservice.model.dto.AuditEventRequest;
import by.finalproject.itacademy.accountservice.model.dto.OperationRequest;
import by.finalproject.itacademy.accountservice.model.dto.PageOfOperation;
import by.finalproject.itacademy.accountservice.model.entity.OperationEntity;
import by.finalproject.itacademy.accountservice.repository.OperationRepository;
import by.finalproject.itacademy.accountservice.service.api.IAccountService;
import by.finalproject.itacademy.accountservice.service.api.IOperationService;
import by.finalproject.itacademy.accountservice.service.exception.CurrencyNotFoundException;
import by.finalproject.itacademy.accountservice.service.exception.OperationServiceException;
import by.finalproject.itacademy.accountservice.service.mapper.OperationMapper;
import by.finalproject.itacademy.accountservice.service.mapper.OperationPageMapper;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.common.jwt.JwtUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class OperationServiceImpl implements IOperationService {
    private final OperationRepository operationRepository;
    private final IAccountService accountService;
    private final AuditServiceClient auditServiceClient;
    private final OperationMapper operationMapper;
    private final OperationPageMapper operationPageMapper;
    private final ClassifierCerviceClient classifierCerviceClient;

    @Transactional
    @Override
    public void createOperation(UUID accountUuid, OperationRequest operationRequest)
            throws AccountNotFoundException {
        log.info("Создание операции для счета: {} пользователя: {}", accountUuid, getCurrentUserUuid().userId());

        if (!accountService.accountExists(accountUuid)) {
            throw new AccountNotFoundException("Счет не найден");
        }

        if (!classifierCerviceClient.getSpecificCurrency(operationRequest.getCurrency())) {
            throw new CurrencyNotFoundException("Указанная валюта не существует");
        }

        if (operationRequest.getCategory() != null
                && !classifierCerviceClient.getSpecificCategory(operationRequest.getCategory())) {
            throw new  CurrencyNotFoundException("Указанная категория не существует");
        }

        try {
            OperationEntity operation = new OperationEntity();
            if (operationRequest.getDate().equals("0")) { // временно
                operation.setDate(LocalDateTime.now());
            }
            operation.setAccount(accountUuid);
            operation.setDate(LocalDateTime.now());
            operation.setDescription(operationRequest.getDescription());
            operation.setCategory(operationRequest.getCategory());
            operation.setValue(operationRequest.getValue());
            operation.setCurrency(operationRequest.getCurrency());

            OperationEntity savedOperation = operationRepository.save(operation);

            accountService.updateBalance(accountUuid, operationRequest.getValue(), getCurrentUserUuid().userId());

            auditServiceClient.logEvent(
                    AuditEventRequest.builder()
                            .jwtUser(getCurrentUserUuid())
                            .userInfo("Создана операция: " + operationRequest.getDescription()
                                    + " на сумму: " + operationRequest.getValue())
                            .essenceId(savedOperation.getUuid())
                            .type(EssenceTypeEnum.OPERATION)
                            .build());

            log.info("Операция успешно создана: {}", savedOperation.getUuid());
        } catch (Exception e) {
                throw new OperationServiceException("ошибка при создании операции");
        }
    }

    @Override
    public PageOfOperation getAccountOperations(UUID accountUuid, Pageable pageable)
            throws AccountNotFoundException {
        log.debug("Получение операций по счету: {} пользователя: {}", accountUuid, getCurrentUserUuid().userId());

        if (!accountService.accountExists(accountUuid)) {
            throw new AccountNotFoundException("Счет не найден");
        }

        Page<OperationEntity> operationsPage = operationRepository.findByAccount(accountUuid, pageable);

        return operationPageMapper.toPageOfUser(operationsPage, operationMapper);
    }

    @Transactional
    @Override
    public void updateOperation(UUID accountUuid, UUID operationUuid, LocalDateTime dtUpdate,
                                OperationRequest operationRequest) throws AccountNotFoundException {
        log.info("Обновление операции: {} счета: {}", operationUuid, accountUuid);

        if (!accountService.accountExists(accountUuid)) {
            throw new AccountNotFoundException("Счет не найден");
        }

        OperationEntity existingOperation = operationRepository.findByUuidAndAccount(operationUuid, accountUuid);
        accountService.updateBalance(accountUuid, existingOperation.getValue().negate(), getCurrentUserUuid().userId());

        existingOperation.setDate(operationRequest.getDate().toLocalDateTime());
        existingOperation.setDescription(operationRequest.getDescription());
        existingOperation.setCategory(operationRequest.getCategory());
        existingOperation.setValue(operationRequest.getValue());
        existingOperation.setCurrency(operationRequest.getCurrency());

        OperationEntity updatedOperation = operationRepository.save(existingOperation);

        accountService.updateBalance(accountUuid, operationRequest.getValue(), getCurrentUserUuid().userId());

        auditServiceClient.logEvent(
                AuditEventRequest.builder()
                        .jwtUser(getCurrentUserUuid())
                        .userInfo("Обновлена операция: " + operationRequest.getDescription())
                        .essenceId(updatedOperation.getUuid())
                        .type(EssenceTypeEnum.OPERATION)
                        .build());
    }

    @Transactional
    @Override
    public void deleteOperation(UUID accountUuid, UUID operationUuid, LocalDateTime dtUpdate)
            throws AccountNotFoundException {
        log.info("Удаление операции: {} счета: {}", operationUuid, accountUuid);

        if (!accountService.accountExists(accountUuid)) {
            throw new AccountNotFoundException("Счет не найден");
        }

        OperationEntity operation = operationRepository.findByUuidAndAccount(operationUuid, accountUuid);
        accountService.updateBalance(accountUuid, operation.getValue().negate(), getCurrentUserUuid().userId());

        operationRepository.delete(operation);

        auditServiceClient.logEvent(
                AuditEventRequest.builder()
                        .jwtUser(getCurrentUserUuid())
                        .userInfo("Удалена операция: " + operation.getDescription())
                        .essenceId(operationUuid)
                        .type(EssenceTypeEnum.OPERATION)
                        .build());

        log.info("Операция успешно удалена: {}", operationUuid);
    }

    public JwtUser getCurrentUserUuid() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
