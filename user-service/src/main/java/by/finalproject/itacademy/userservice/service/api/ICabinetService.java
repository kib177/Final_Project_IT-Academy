package by.finalproject.itacademy.userservice.service.api;

import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserLogin;
import by.finalproject.itacademy.userservice.model.dto.UserRegistration;

import java.util.Optional;
import java.util.UUID;

public interface ICabinetService {
    boolean registration(UserRegistration userRegistration);
    String login(UserLogin userLogin);
    boolean verifyUser(String mail, String code);
    Optional<User> getAboutSelf();
    UUID getCurrentUserUuid();
}
