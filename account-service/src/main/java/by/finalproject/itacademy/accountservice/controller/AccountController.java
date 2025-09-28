package by.finalproject.itacademy.accountservice.controller;

import by.finalproject.itacademy.accountservice.model.dto.AccountResponse;
import by.finalproject.itacademy.accountservice.model.dto.AccountRequest;
import by.finalproject.itacademy.accountservice.model.dto.PageOfAccount;
import by.finalproject.itacademy.accountservice.service.api.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final IAccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createAccount(@RequestBody AccountRequest dto) {
        accountService.createAccount(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageOfAccount> getAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok().body(accountService.getUserAccounts(PageRequest.of(page, size)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable UUID uuid) throws AccountNotFoundException {
        return ResponseEntity.ok().body(accountService.getAccount(uuid));
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> updateAccount(
            @PathVariable UUID uuid,
            @PathVariable("dt_update") LocalDateTime dtUpdate,
            @RequestBody AccountRequest dto) throws AccountNotFoundException {
        accountService.updateAccount(uuid, dtUpdate, dto);
        return ResponseEntity.ok().build();
    }
}