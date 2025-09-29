package by.finalproject.itacademy.userservice.controller;

import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserLogin;
import by.finalproject.itacademy.userservice.model.dto.UserRegistration;
import by.finalproject.itacademy.userservice.service.api.ICabinetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/cabinet")
@RequiredArgsConstructor
public class CabinetController {
    private final ICabinetService cabinetService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody UserRegistration userRegistration)
            throws BadRequestException {
        cabinetService.registration(userRegistration);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping( "/verification")
    public ResponseEntity<?> verification(@RequestParam String code, @RequestParam String mail) throws BadRequestException {
        cabinetService.verifyUser(mail, code);
            return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLogin userLogin) throws BadRequestException {
            String result = cabinetService.login(userLogin);
            return ResponseEntity.ok(result);
    }

    @GetMapping("/me")
    public ResponseEntity<User> aboutSelf() throws BadRequestException {
        User user = cabinetService.getAboutSelf();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
