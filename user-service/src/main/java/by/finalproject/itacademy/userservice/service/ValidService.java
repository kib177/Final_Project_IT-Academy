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
    public void validateUserData(UserCreate userCreate) throws StructuredValidationException {
        StructuredErrorResponse responses = new StructuredErrorResponse();

        if (userCreate.getMail() == null || !isValidEmail(userCreate.getMail())) {
            responses.addError(new FieldError("mail", "Электронная почта должна быть корректной"));
        }

        if (userCreate.getFio() == null || userCreate.getFio().trim().length() < 2) {
            responses.addError(new FieldError("fio", "ФИО должно содержать минимум 2 символа"));
        }

        if (userCreate.getPassword() != null && userCreate.getPassword().length() < 6) {
            responses.addError(new FieldError("password", "Пароль должен содержать минимум 6 символов"));
        }

        if (!responses.checkErrors()) {
            throw new StructuredValidationException("Данные пользователя содержат ошибки", responses);
        }
    }

    public void validateRegistrationData(UserRegistration userRegistration) {
        StructuredErrorResponse responses = new StructuredErrorResponse();

        if (userRegistration.getMail() == null || !isValidEmail(userRegistration.getMail())) {
            responses.addError(new FieldError("mail", "Электронная почта должна быть корректной"));
        }

        if (userRegistration.getFio() == null || userRegistration.getFio().trim().length() < 2) {
            responses.addError(new FieldError("fio", "ФИО должно содержать минимум 2 символа"));
        }

        if (userRegistration.getPassword() == null || userRegistration.getPassword().length() < 6) {
            responses.addError(new FieldError("password", "Пароль должен содержать минимум 6 символов"));
        }

        if (!responses.checkErrors()) {
            throw new StructuredValidationException("Данные пользователя содержат ошибки", responses);
        }
    }

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
