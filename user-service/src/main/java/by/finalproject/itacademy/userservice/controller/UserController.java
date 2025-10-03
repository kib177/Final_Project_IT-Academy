package by.finalproject.itacademy.userservice.controller;

import by.finalproject.itacademy.userservice.model.dto.PageOfUser;
import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserCreate;
import by.finalproject.itacademy.userservice.service.api.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreate userCreate) throws BadRequestException {
        userService.create(userCreate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PageOfUser> getPageOfUsers( @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersPage(PageRequest.of(page, size)));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> getUser(@PathVariable UUID uuid) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getById(uuid));
    }

    @PutMapping("/users/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> updateUser(
            @PathVariable UUID uuid,
            @PathVariable Long dt_update,
            @RequestBody @Valid UserCreate userCreate) throws BadRequestException {
       userService.updateUser(uuid, dt_update, userCreate);
        return ResponseEntity.ok().build();
    }
}
