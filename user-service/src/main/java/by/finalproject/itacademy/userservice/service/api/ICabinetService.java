package by.finalproject.itacademy.userservice.service.api;

import by.finalproject.itacademy.userservice.dto.User;
import by.finalproject.itacademy.userservice.dto.UserLogin;
import by.finalproject.itacademy.userservice.dto.UserRegistration;

import java.util.Optional;
import java.util.UUID;

public interface ICabinetService {
    boolean registration(UserRegistration userRegistration);
    String login(UserLogin userLogin);
    Optional<User> getById(UUID uuid);
    boolean verifyUser(String mail, String code);
}
