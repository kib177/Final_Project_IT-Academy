package by.finalproject.itacademy.accountservice.service;

import by.finalproject.itacademy.accountservice.model.dto.AccountDTO;
import by.finalproject.itacademy.accountservice.model.dto.AccountRequest;
import by.finalproject.itacademy.accountservice.model.dto.PageOfAccount;
import by.finalproject.itacademy.accountservice.model.entity.AccountEntity;
import by.finalproject.itacademy.accountservice.repository.AccountRepository;
import by.finalproject.itacademy.accountservice.repository.OperationRepository;
import by.finalproject.itacademy.accountservice.service.api.IAccountService;
import by.finalproject.itacademy.common.jwt.JwtUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;

    @Transactional
    @Override
    public void save(AccountRequest account) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID currencyUuid;
        if(isValidUuid(account.getCurrency())) {
            currencyUuid = classifierServiceClient.getCurrency(UUID.fromString(account.getCurrency())).getUuid();
        }
        else {
            currencyUuid = classifierServiceClient.getCurrency(account.getCurrency()).getUuid();
        }

        AccountEntity accountEntity = AccountEntity.builder()
                .dtCreate(LocalDateTime.now())
                .dtUpdate(LocalDateTime.now())
                .title(account.getTitle())
                .description(account.getDescription())
                .balance(account.getBalance())
                .type(account.getType())
                .balance(account.getBalance())
                .currency(currencyUuid)
                .userId(jwtUser.userId())
                .build();
        accountRepository.save(accountEntity);
    }

    @Override
    public AccountDTO getAccount(UUID uuid) {
        AccountEntity entity = accountRepository.getById(uuid);
        return AccountDTO.builder()
                .uuid(entity.getUuid())
                .dtCreate(entity.getDtCreate())
                .dtUpdate(entity.getDtUpdate())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .balance(entity.getBalance())
                .type(entity.getType())
                .balance(entity.getBalance())
                .build();
    }

    @Transactional
    @Override
    public void update(UUID uuid, AccountDTO account, LocalDateTime dtUpdate) {
        AccountEntity entity = accountRepository.getReferenceById(uuid);
        if (!Objects.equals(account.getTitle(), entity.getTitle())) {
            entity.setTitle(account.getTitle());
        }
        if (!Objects.equals(account.getDescription(), entity.getDescription())) {
            entity.setDescription(account.getDescription());
        }
        if (!Objects.equals(account.getBalance(), entity.getBalance())) {
            entity.setBalance(account.getBalance());
        }
        if (account.getType() != entity.getType()) {
            entity.setType(account.getType());
        }
        entity.setDtUpdate(dtUpdate);
        accountRepository.save(entity);
    }

    @Override
    public PageOfAccount getPage(UUID userUuid, int page, int size) {
        Page<AccountEntity> accountPage = accountRepository.findByAll(userUuid, PageRequest.of(page, size));
        List<AccountDTO> content = new ArrayList<>();
        for (AccountEntity accountEntity : accountPage.getContent()) {
            content.add(AccountDTO.builder()
                    .uuid(accountEntity.getUuid())
                    .dtCreate(accountEntity.getDtCreate())
                    .dtUpdate(accountEntity.getDtUpdate())
                    .title(accountEntity.getTitle())
                    .description(accountEntity.getDescription())
                    .balance(accountEntity.getBalance())
                    .type(accountEntity.getType())
                    .balance(accountEntity.getBalance())
                    .build());
        }
        return PageOfAccount.builder()
                .number(accountPage.getNumber())
                .size(accountPage.getSize())
                .totalPages(accountPage.getTotalPages())
                .totalElements(accountPage.getTotalElements())
                .first(accountPage.isFirst())
                .numberOfElements(accountPage.getNumberOfElements())
                .last(accountPage.isLast())
                .content(content)
                .build();

    }
}
