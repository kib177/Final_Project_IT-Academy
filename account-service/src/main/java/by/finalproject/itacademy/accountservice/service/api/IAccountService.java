package by.finalproject.itacademy.accountservice.service.api;

import by.finalproject.itacademy.accountservice.model.dto.AccountResponse;
import by.finalproject.itacademy.accountservice.model.dto.AccountRequest;
import by.finalproject.itacademy.accountservice.model.dto.PageOfAccount;
import by.finalproject.itacademy.accountservice.model.entity.AccountEntity;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IAccountService {
    @Transactional
    void createAccount(AccountRequest accountRequest);

    @SneakyThrows
    void updateAccount(UUID uuid, LocalDateTime dtUpdate, AccountRequest accountRequest);

    PageOfAccount getUserAccounts(Pageable pageable);

    @SneakyThrows
    AccountResponse getAccount(UUID uuid);

    @SneakyThrows
    void updateBalance(UUID accountUuid, BigDecimal amount, UUID userUuid);

    boolean accountExists(UUID accountUuid);

    @SneakyThrows
    BigDecimal getAccountBalance(UUID accountUuid, UUID userUuid);

    List<AccountEntity> getUserAccountsForOperations(UUID userUuid, List<UUID> accountUuids);
}
