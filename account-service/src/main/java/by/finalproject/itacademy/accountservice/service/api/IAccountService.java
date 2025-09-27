package by.finalproject.itacademy.accountservice.service.api;

import by.finalproject.itacademy.accountservice.model.dto.AccountDTO;
import by.finalproject.itacademy.accountservice.model.dto.AccountRequest;
import by.finalproject.itacademy.accountservice.model.dto.OperationDTO;
import by.finalproject.itacademy.common.model.dto.PageDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface IAccountService {
    @Transactional
    void createAccount(AccountDTO dto);

    @Transactional(readOnly = true)
    PageDTO<AccountDTO> getUserAccounts(int page, int size);

    @Transactional
    void save(AccountRequest account);

    @Transactional
    void update(UUID uuid, AccountDTO account, long dtUpdate);
}
