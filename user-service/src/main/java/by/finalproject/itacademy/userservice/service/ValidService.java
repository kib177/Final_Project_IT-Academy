package by.finalproject.itacademy.userservice.service;

import by.finalproject.itacademy.common.exception.StructuredValidationException;
import by.finalproject.itacademy.common.model.dto.FieldError;
import by.finalproject.itacademy.common.model.dto.StructuredErrorResponse;
import by.finalproject.itacademy.userservice.model.dto.UserCreate;
import by.finalproject.itacademy.userservice.model.dto.UserRegistration;
import org.springframework.stereotype.Service;

import java.util.List;
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
