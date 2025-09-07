package com.example.finalProject.controller;

import com.example.finalProject.dto.PageOfUser;
import com.example.finalProject.dto.User;
import com.example.finalProject.dto.UserCreate;
import com.example.finalProject.service.api.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    /*@PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserCreate userCreate) {
        userService.create(userCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful. Check email for verification.");
    }*/

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageOfUser<Object>> getUsers(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersPage(pageable));
    }

    @GetMapping("/{uuid}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUser(@PathVariable UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createUser(@RequestBody List<UserCreate> userCreate) {
        for(UserCreate userCreate1 : userCreate) {
            userService.create(userCreate1);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful. Check email for verification.");
    }
}
