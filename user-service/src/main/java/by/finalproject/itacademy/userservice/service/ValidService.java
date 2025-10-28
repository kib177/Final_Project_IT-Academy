package by.finalproject.itacademy.userservice.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValidService {

    public void isValidEmail(String email) {
        if(email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}
