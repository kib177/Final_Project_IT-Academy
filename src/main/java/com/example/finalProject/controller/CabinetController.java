package com.example.finalProject.controller;

import com.example.finalProject.dto.UserLogin;
import com.example.finalProject.dto.UserRegistration;
import com.example.finalProject.service.api.ICabinetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> login(@RequestBody UserLogin userLogin) {
        try {
            String result = cabinetService.login(userLogin);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<String> aboutSelf() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    
}
