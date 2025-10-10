package by.finalproject.itacademy.accountservice.service;

import by.finalproject.itacademy.accountservice.feign.ClassifierCerviceClient;
import by.finalproject.itacademy.accountservice.model.dto.OperationRequest;
import by.finalproject.itacademy.accountservice.model.dto.PageOfOperation;
import by.finalproject.itacademy.accountservice.model.entity.OperationEntity;
import by.finalproject.itacademy.accountservice.repository.OperationRepository;
import by.finalproject.itacademy.accountservice.service.api.IAccountService;
import by.finalproject.itacademy.accountservice.service.api.IAuditLogEventService;
import by.finalproject.itacademy.accountservice.service.api.IOperationService;
import by.finalproject.itacademy.accountservice.service.exception.ClassifierNotFoundException;
import by.finalproject.itacademy.accountservice.service.exception.OperationServiceException;
import by.finalproject.itacademy.accountservice.service.exception.AccountNotFoundException;
import by.finalproject.itacademy.accountservice.service.mapper.OperationMapper;
import by.finalproject.itacademy.accountservice.service.mapper.OperationPageMapper;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.common.jwt.JwtUser;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class OperationServiceImpl implements IOperationService {
    private final OperationRepository operationRepository;
    private final IAccountService accountService;
    private final OperationMapper operationMapper;
    private final OperationPageMapper operationPageMapper;
    private final ClassifierCerviceClient classifierCerviceClient;
    private final IAuditLogEventService auditLogEventService;

    @Transactional
    @Override
    public void createOperation(UUID accountUuid, OperationRequest operationRequest) {
        log.info("Создание операции для счета: {} пользователя: {}", accountUuid,
                getCurrentUser().userId());

        if (!accountService.accountExists(accountUuid)) {
            throw new AccountNotFoundException("Счет не найден");
        }
        try {
            if (!classifierCerviceClient.getSpecificCurrency(operationRequest.getCurrency())) {
                throw new ClassifierNotFoundException("Указанная валюта не существует");
            }

            if (operationRequest.getCategory() != null
                    && !classifierCerviceClient.getSpecificCategory(operationRequest.getCategory())) {
                throw new ClassifierNotFoundException("Указанная категория не существует");
            }
        } catch (FeignException e) {
            throw new ServiceException("Сервис классификатора недоступен", e);
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

            accountService.updateBalance(accountUuid, operationRequest.getValue());

            auditLogEventService.sendAudit(getCurrentUser(),
                    "Создана операция: " + operationRequest.getDescription(),
                    savedOperation.getUuid(),
                    EssenceTypeEnum.OPERATION);


            log.info("Операция успешно создана: {}", savedOperation.getUuid());
        } catch (OperationServiceException e) {
            throw new OperationServiceException("ошибка при создании операции");
        }
    }

    @Override
    public PageOfOperation getAccountOperations(UUID accountUuid, Pageable pageable) {
        log.debug("Получение операций по счету: {} пользователя: {}", accountUuid,
                getCurrentUser().userId());

        if (!accountService.accountExists(accountUuid)) {
            throw new AccountNotFoundException("Счет не найден");
        }

        Page<OperationEntity> operationsPage = operationRepository.findByAccount(accountUuid, pageable);

        return operationPageMapper.toPageOfUser(operationsPage, operationMapper);
    }

    @Transactional
    @Override
    public void updateOperation(UUID accountUuid, UUID operationUuid, LocalDateTime dtUpdate,
                                OperationRequest operationRequest) {
        log.info("Обновление операции: {} счета: {}",
                operationUuid,
                accountUuid);

        if (!accountService.accountExists(accountUuid)) {
            throw new AccountNotFoundException("Счет не найден");
        }

        OperationEntity existingOperation = operationRepository.findByUuidAndAccount(operationUuid, accountUuid);
        accountService.updateBalance(accountUuid, existingOperation.getValue().negate());

        existingOperation.setDate(operationRequest.getDate().toLocalDateTime());
        existingOperation.setDescription(operationRequest.getDescription());
        existingOperation.setCategory(operationRequest.getCategory());
        existingOperation.setValue(operationRequest.getValue());
        existingOperation.setCurrency(operationRequest.getCurrency());

        OperationEntity updatedOperation = operationRepository.save(existingOperation);

        accountService.updateBalance(accountUuid, operationRequest.getValue());

        auditLogEventService.sendAudit(getCurrentUser(),
                "Обновлена операция: " + operationRequest.getDescription(),
                updatedOperation.getUuid(),
                EssenceTypeEnum.OPERATION);

    }

    @Transactional
    @Override
    public void deleteOperation(UUID accountUuid, UUID operationUuid, LocalDateTime dtUpdate) {
        log.info("Удаление операции: {} счета: {}", operationUuid, accountUuid);

        if (!accountService.accountExists(accountUuid)) {
            throw new AccountNotFoundException("Счет не найден");
        }

        OperationEntity operation = operationRepository.findByUuidAndAccount(operationUuid, accountUuid);
        accountService.updateBalance(accountUuid, operation.getValue().negate());

        operationRepository.delete(operation);

        auditLogEventService.sendAudit(getCurrentUser(),
                "Удалена операция: " + operation.getDescription(),
                operationUuid,
                EssenceTypeEnum.OPERATION);

        log.info("Операция успешно удалена: {}", operationUuid);
    }

    public JwtUser getCurrentUser() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
