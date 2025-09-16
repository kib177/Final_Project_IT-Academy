package by.finalproject.itacademy.accountservice.controller;

import by.finalproject.itacademy.accountservice.model.dto.AccountDTO;
import by.finalproject.itacademy.accountservice.model.entity.AccountEntity;
import by.finalproject.itacademy.accountservice.service.AccountServiceImpl;
import by.finalproject.itacademy.accountservice.service.api.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createAccount(@RequestBody AccountDTO dto) {
        return ResponseEntity.ok(accountService.createAccount(dto));
    }

    @GetMapping
    public Page<AccountEntity> getAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return accountService.getUserAccounts(page, size);
    }

    @GetMapping("/{uuid}")
    public AccountEntity getAccount(@PathVariable UUID uuid) {
        return accountService.get(uuid);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public AccountEntity updateAccount(
            @PathVariable UUID uuid,
            @PathVariable("dt_update") Long dtUpdate,
            @RequestBody AccountDTO dto) {
        return accountService.update(uuid, dtUpdate, dto);
    }
}