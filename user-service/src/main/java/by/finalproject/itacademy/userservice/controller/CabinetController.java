package by.finalproject.itacademy.userservice.controller;


import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserLogin;
import by.finalproject.itacademy.userservice.model.dto.UserRegistration;
import by.finalproject.itacademy.userservice.service.api.ICabinetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/cabinet")
@RequiredArgsConstructor
public class CabinetController {
    private final ICabinetService cabinetService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody UserRegistration userRegistration) {
        cabinetService.registration(userRegistration);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful. Check email for verification.");
    }

    @GetMapping("/verification")
    public ResponseEntity<String> verification(@RequestParam String code, @RequestParam String mail) {
        boolean isVerified = cabinetService.verifyUser(mail, code);
        if (isVerified) {
            return ResponseEntity.ok("Account verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification code");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLogin userLogin) {
        try {
            String result = cabinetService.login(userLogin);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<User> aboutSelf(UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(cabinetService.getById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }


}
