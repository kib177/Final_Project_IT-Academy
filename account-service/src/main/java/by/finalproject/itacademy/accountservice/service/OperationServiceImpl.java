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
import by.finalproject.itacademy.accountservice.service.mapper.OperationMapper;
import by.finalproject.itacademy.accountservice.service.mapper.OperationPageMapper;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.common.exception.DataVersionException;
import by.finalproject.itacademy.common.exception.OperationNotFoundException;
import by.finalproject.itacademy.common.jwt.JwtUser;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
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
        log.info("Создание операции для счета: {} пользователя: {}", accountUuid, getCurrentUserUuid());

        if (!accountService.accountExists(accountUuid)) {
            throw new AccountNotFoundException("Счет не найден");
        }

        if (!classifierCerviceClient.getSpecificCurrency("Bearer " + SecurityContextHolder.getContext(), operationRequest.getCurrency())) {
            throw new ValidationException("Указанная валюта не существует");
        }

        if (operationRequest.getCategory() != null
                && !classifierCerviceClient.getSpecificCategory("Bearer " + SecurityContextHolder.getContext(), operationRequest.getCategory())) {
            throw new ValidationException("Указанная категория не существует");
        }

        OperationEntity operation = new OperationEntity();
        operation.setAccount(accountUuid);
        operation.setDate(LocalDateTime.now());
        operation.setDescription(operationRequest.getDescription());
        operation.setCategory(operationRequest.getCategory());
        operation.setValue(operationRequest.getValue());
        operation.setCurrency(operationRequest.getCurrency());

        OperationEntity savedOperation = operationRepository.save(operation);

        accountService.updateBalance(accountUuid, operationRequest.getValue(), getCurrentUserUuid());

        auditServiceClient.logEvent("Bearer " + SecurityContextHolder.getContext(),
                AuditEventRequest.builder()
                .userUuid(getCurrentUserUuid())
                .userInfo("Создана операция: " + operationRequest.getDescription() + " на сумму: " + operationRequest.getValue())
                .essenceId(savedOperation.getUuid().toString())
                .type(EssenceTypeEnum.OPERATION)
                .build());

        log.info("Операция успешно создана: {}", savedOperation.getUuid());
    }

    @Override
    public PageOfOperation getAccountOperations(UUID accountUuid, Pageable pageable) throws AccountNotFoundException {
        log.debug("Получение операций по счету: {} пользователя: {}", accountUuid, getCurrentUserUuid());

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

        OperationEntity existingOperation = operationRepository.findByUuidAndAccount(operationUuid, accountUuid)
                .orElseThrow(() -> new OperationNotFoundException("Операция не найдена"));

        if (!existingOperation.getDtUpdate().equals(dtUpdate)) {
            throw new DataVersionException("Конфликт версий данных. Операция была изменена другим пользователем");
        }

        accountService.updateBalance(accountUuid, existingOperation.getValue().negate(), getCurrentUserUuid());

        existingOperation.setDate(operationRequest.getDate());
        existingOperation.setDescription(operationRequest.getDescription());
        existingOperation.setCategory(operationRequest.getCategory());
        existingOperation.setValue(operationRequest.getValue());
        existingOperation.setCurrency(operationRequest.getCurrency());

        OperationEntity updatedOperation = operationRepository.save(existingOperation);

        accountService.updateBalance(accountUuid, operationRequest.getValue(), getCurrentUserUuid());

        auditServiceClient.logEvent("Bearer " + SecurityContextHolder.getContext(),
                AuditEventRequest.builder()
                .userUuid(getCurrentUserUuid())
                .userInfo( "Обновлена операция: " + operationRequest.getDescription())
                .essenceId(updatedOperation.getUuid().toString())
                .type(EssenceTypeEnum.OPERATION)
                .build());
    }

    @Transactional
    @Override
    public void deleteOperation(UUID accountUuid, UUID operationUuid, LocalDateTime dtUpdate) throws AccountNotFoundException {
        log.info("Удаление операции: {} счета: {}", operationUuid, accountUuid);

        if (!accountService.accountExists(accountUuid)){
            throw new AccountNotFoundException("Счет не найден");
        }

        OperationEntity operation = operationRepository.findByUuidAndAccount(operationUuid, accountUuid)
                .orElseThrow(() -> new OperationNotFoundException("Операция не найдена"));

        if (!operation.getDtUpdate().equals(dtUpdate)) {
            throw new DataVersionException("Конфликт версий данных. Операция была изменена другим пользователем");
        }

        accountService.updateBalance(accountUuid, operation.getValue().negate(), getCurrentUserUuid());

        operationRepository.delete(operation);

        auditServiceClient.logEvent("Bearer " + SecurityContextHolder.getContext(),
                AuditEventRequest.builder()
                        .userUuid(getCurrentUserUuid())
                        .userInfo( "Удалена операция: " + operation.getDescription())
                        .essenceId(operationUuid.toString())
                        .type(EssenceTypeEnum.OPERATION)
                        .build());

        log.info("Операция успешно удалена: {}", operationUuid);
    }

    public UUID getCurrentUserUuid() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtUser.userId();
    }
}
