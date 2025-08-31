package com.example.finalProject.controller;


import com.example.finalProject.dto.UserLogin;
import com.example.finalProject.dto.UserRegistration;
import com.example.finalProject.service.api.ICabinetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cabinet")
@RequiredArgsConstructor
public class CabinetController {

    ICabinetService cabinetService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody UserRegistration userRegistration) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/verification")
    public ResponseEntity<String> verification(@RequestParam String code, @RequestParam String mail) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLogin userLogin) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/me")
    public ResponseEntity<String> aboutSelf() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    
}
