package by.finalproject.itacademy.accountservice.service;

import by.finalproject.itacademy.accountservice.feign.AuditServiceClient;
import by.finalproject.itacademy.accountservice.feign.ClassifierCerviceClient;
import by.finalproject.itacademy.accountservice.model.dto.AccountResponse;
import by.finalproject.itacademy.accountservice.model.dto.AccountRequest;
import by.finalproject.itacademy.accountservice.model.dto.AuditEventRequest;
import by.finalproject.itacademy.accountservice.model.dto.PageOfAccount;
import by.finalproject.itacademy.accountservice.model.entity.AccountEntity;
import by.finalproject.itacademy.accountservice.repository.AccountRepository;
import by.finalproject.itacademy.accountservice.service.api.IAccountService;
import by.finalproject.itacademy.accountservice.service.mapper.AccountMapper;
import by.finalproject.itacademy.accountservice.service.mapper.AccountPageMapper;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.common.exception.DataVersionException;
import by.finalproject.itacademy.common.exception.InsufficientFundsException;
import by.finalproject.itacademy.common.jwt.JwtTokenUtil;
import by.finalproject.itacademy.common.jwt.JwtUser;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final AuditServiceClient auditServiceClient;
    private final ClassifierCerviceClient classifierCerviceClient;
    private final AccountMapper accountMapper;
    private final AccountPageMapper pageMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private String jwt;

    @Transactional
    @Override
    public void createAccount(AccountRequest accountRequest) {
        jwt = getJwtToken();
        log.info("Создание счета для пользователя: {}", getCurrentUserUuid());

        if (!classifierCerviceClient.getSpecificCurrency(
                "Bearer " + jwt,
                accountRequest.getCurrency())) {
            throw new ValidationException("Указанная валюта не существует");
        }

        AccountEntity savedAccount = accountRepository.save(AccountEntity.builder()
                .dtCreate(LocalDateTime.now())
                .dtUpdate(LocalDateTime.now())
                .title(accountRequest.getTitle())
                .description(accountRequest.getDescription())
                .type(accountRequest.getType())
                .currency(accountRequest.getCurrency())
                .userUuid(getCurrentUserUuid())
                .balance(BigDecimal.ZERO)
                .build());


        auditServiceClient.logEvent("Bearer " + jwt, AuditEventRequest.builder()
                        .userUuid(getCurrentUserUuid())
                        .userInfo("Создан новый счет: " + accountRequest.getTitle())
                        .essenceId(savedAccount.getUuid().toString())
                .type(EssenceTypeEnum.ACCOUNT)
                .build());

        log.info("Счет успешно создан: {}", savedAccount.getUuid());
    }


    @Override
    public void updateAccount(UUID uuid, LocalDateTime dtUpdate, AccountRequest accountRequest) throws AccountNotFoundException {
        log.info("Обновление счета: {} пользователя: {}", uuid, getCurrentUserUuid());

        AccountEntity existingAccount = accountRepository.findByUuid(uuid)
                .orElseThrow(() -> new AccountNotFoundException("Счет не найден"));

        if (!existingAccount.getDtUpdate().equals(dtUpdate)) {
            throw new DataVersionException("Конфликт версий данных. Счет был изменен другим пользователем");
        }

        if (!classifierCerviceClient.getSpecificCurrency(
                "Bearer " + SecurityContextHolder.getContext(), accountRequest.getCurrency())) {
            throw new ValidationException("Указанная валюта не существует");
        }

        AccountEntity updatedAccount = accountRepository.save(accountMapper.toEntity(accountRequest));

        // Аудит
        auditServiceClient.logEvent("Bearer " + SecurityContextHolder.getContext(),
                AuditEventRequest.builder()
                .userUuid(getCurrentUserUuid())
                .userInfo("Создан новый счет: " + accountRequest.getTitle())
                .essenceId(updatedAccount.getUuid().toString())
                .type(EssenceTypeEnum.ACCOUNT)
                .build());

        log.info("Счет успешно обновлен: {}", updatedAccount.getUuid());
    }

    @Override
    public PageOfAccount getUserAccounts(Pageable pageable) {
        log.debug("Получение счетов пользователя: {}, страница: {}", getCurrentUserUuid(), pageable.getPageNumber());

        Page<AccountEntity> accountsPage = accountRepository.findByUserUuid(getCurrentUserUuid(), pageable);
        return pageMapper.toPageOfUser(accountsPage, accountMapper);
    }

    @Override
    public AccountResponse getAccount(UUID uuid) throws AccountNotFoundException {
        log.debug("Получение счета: {} пользователя: {}", uuid, getCurrentUserUuid());

        AccountEntity account = accountRepository.findByUuid(uuid)
                .orElseThrow(() -> new AccountNotFoundException("Счет не найден"));

        return accountMapper.toDto(account);
    }

    @Override
    public void updateBalance(UUID accountUuid, BigDecimal amount, UUID userUuid) throws AccountNotFoundException {
        log.info("Обновление баланса счета: {} на сумму: {}", accountUuid, amount);

        AccountEntity account = accountRepository.findByUuidAndUserUuid(accountUuid, userUuid)
                .orElseThrow(() -> new AccountNotFoundException("Счет не найден"));

        BigDecimal newBalance = account.getBalance().add(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на счете");
        }

        account.setBalance(newBalance);
        accountRepository.save(account);

        log.debug("Баланс счета {} обновлен: {}", accountUuid, newBalance);
    }

    @Override
    public boolean accountExists(UUID accountUuid) {
        return accountRepository.existsById(accountUuid);
    }


    @Override
    public BigDecimal getAccountBalance(UUID accountUuid, UUID userUuid) throws AccountNotFoundException {
        return accountRepository.findBalanceByUuidAndUserUuid(accountUuid, userUuid)
                .orElseThrow(() -> new AccountNotFoundException("Счет не найден"));
    }

    @Override
    public List<AccountEntity> getUserAccountsForOperations(UUID userUuid, List<UUID> accountUuids) {
        if (accountUuids == null || accountUuids.isEmpty()) {
            return accountRepository.findByUserUuid(userUuid, Pageable.unpaged()).getContent();
        }
        return accountRepository.findByUserUuidAndUuidIn(userUuid, accountUuids);
    }

    public UUID getCurrentUserUuid() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtUser.userId();
    }

    public String getJwtToken(){
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtTokenUtil.generateToken(jwtUser.userId(), jwtUser.email(), jwtUser.fio(), jwtUser.role());
    }
}

