package by.finalproject.itacademy.accountservice.service;

import by.finalproject.itacademy.accountservice.feign.AuditServiceClient;
import by.finalproject.itacademy.accountservice.model.dto.AccountDTO;
import by.finalproject.itacademy.accountservice.model.dto.OperationDTO;
import by.finalproject.itacademy.accountservice.model.entity.AccountEntity;
import by.finalproject.itacademy.accountservice.model.entity.OperationEntity;
import by.finalproject.itacademy.accountservice.repository.AccountRepository;
import by.finalproject.itacademy.accountservice.repository.OperationRepository;
import by.finalproject.itacademy.accountservice.service.api.IAccountService;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.common.model.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    @Transactional
    @Override
    public void createAccount(AccountDTO dto) {
        LocalDateTime now = LocalDateTime.now();

        accountRepository.save(AccountEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .balance(dto.getBalance())
                .type(dto.getType())
                .currency(dto.getCurrency())
                .dtCreate(now)
                .dtUpdate(now)
                .build());
    }

    @Transactional(readOnly = true)
    @Override
    public PageDTO<AccountDTO> getUserAccounts(int page, int size) {
        UUID userUuid = getCurrentUserUuid();
        Page<AccountEntity> accountPage = accountRepository.findByUserUuid(
                userUuid, PageRequest.of(page, size, Sort.by("dtCreate").descending()));

        return convertToPageDTO(accountPage);
    }

    @Transactional
    @Override
    public void createOperation(UUID accountUuid, OperationDTO dto) {
        AccountEntity account = accountRepository.findById(accountUuid)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Verify the account belongs to the current user
        if (!account.getUuid().equals(userUuid)) {
            throw new RuntimeException("Access denied");
        }

        OperationEntity operation = new OperationEntity();
        operation.setDate(dto.getDate());
        operation.setDescription(dto.getDescription());
        operation.setCategory(dto.getCategory());
        operation.setValue(dto.getValue());
        operation.setCurrency(dto.getCurrency());
        operation.setAccountUuid(accountUuid);
        operation.setDtCreate(Instant.now().getEpochSecond());
        operation.setDtUpdate(Instant.now().getEpochSecond());

        operationRepository.save(operation);

        // Update account balance
        account.setBalance(account.getBalance() + dto.getValue());
        account.setDtUpdate(Instant.now().getEpochSecond());
        accountRepository.save(account);

        // Log audit event
        auditServiceClient.createAuditRecord(
                userUuid,
                "Created operation: " + dto.getDescription() + " for account: " + account.getTitle(),
                EssenceTypeEnum.OPERATION,
                operation.getUuid().toString()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageDTO<OperationDTO> getAccountOperations(UUID accountUuid, int page, int size) {
        UUID userUuid = getCurrentUserUuid();

        AccountEntity account = accountRepository.findById(accountUuid)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Verify the account belongs to the current user
        if (!account.getUuid().equals(userUuid)) {
            throw new RuntimeException("Access denied");
        }

        Page<OperationEntity> operationPage = operationRepository.findByAccountUuid(
                accountUuid, PageRequest.of(page, size, Sort.by("date").descending()));

        return convertToOperationPageDTO(operationPage);
    }

    private UUID getCurrentUserUuid() {
        // Extract user UUID from JWT token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // In a real implementation, you would extract UUID from the token or user details
        // For this example, we'll use a simplified approach
        return UUID.nameUUIDFromBytes(username.getBytes());
    }

    private AccountDTO convertToDTO(AccountEntity account) {
        AccountDTO dto = new AccountDTO();
        dto.setUuid(account.getUuid());
        dto.setDtCreate(account.getDtCreate());
        dto.setDtUpdate(account.getDtUpdate());
        dto.setTitle(account.getTitle());
        dto.setDescription(account.getDescription());
        dto.setBalance(account.getBalance());
        dto.setType(account.getType());
        dto.setCurrency(account.getCurrency());
        return dto;
    }

    private OperationDTO convertToDTO(OperationEntity operation) {
        OperationDTO dto = new OperationDTO();
        dto.setUuid(operation.getUuid());
        dto.setDtCreate(operation.getDtCreate());
        dto.setDtUpdate(operation.getDtUpdate());
        dto.setDate(operation.getDate());
        dto.setDescription(operation.getDescription());
        dto.setCategory(operation.getCategory());
        dto.setValue(operation.getValue());
        dto.setCurrency(operation.getCurrency());
        return dto;
    }

    private PageDTO<AccountDTO> convertToPageDTO(Page<AccountEntity> page) {
        PageDTO<AccountDTO> pageDTO = new PageDTO<>();
        pageDTO.setNumber(page.getNumber());
        pageDTO.setSize(page.getSize());
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements(page.getTotalElements());
        pageDTO.setFirst(page.isFirst());
        pageDTO.setNumberOfElements(page.getNumberOfElements());
        pageDTO.setLast(page.isLast());
        pageDTO.setContent(page.map(this::convertToDTO).getContent());
        return pageDTO;
    }

    private PageDTO<OperationDTO> convertToOperationPageDTO(Page<OperationEntity> page) {
        PageDTO<OperationDTO> pageDTO = new PageDTO<>();
        pageDTO.setNumber(page.getNumber());
        pageDTO.setSize(page.getSize());
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements(page.getTotalElements());
        pageDTO.setFirst(page.isFirst());
        pageDTO.setNumberOfElements(page.getNumberOfElements());
        pageDTO.setLast(page.isLast());
        pageDTO.setContent(page.map(this::convertToDTO).getContent());
        return pageDTO;
    }
}
