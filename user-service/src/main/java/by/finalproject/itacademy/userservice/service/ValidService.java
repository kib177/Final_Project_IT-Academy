package by.finalproject.itacademy.userservice.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValidService {

    public boolean isValidUuid(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
