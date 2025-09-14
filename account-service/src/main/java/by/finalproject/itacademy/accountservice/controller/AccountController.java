package by.finalproject.itacademy.accountservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @PostMapping
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        // реализация
    }

    @GetMapping("/{uuid}/operation")
    public ResponseEntity<Page<Operation>> getOperations(
            @PathVariable UUID uuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        // реализация
    }
}