package com.example.finalProject.service.api;

import com.example.finalProject.dto.User;
import com.example.finalProject.dto.UserLogin;
import com.example.finalProject.dto.UserRegistration;

import java.util.Optional;
import java.util.UUID;

public interface ICabinetService {
    boolean registration(UserRegistration userRegistration);
    String login(UserLogin userLogin);
    Optional<User> getById(UUID uuid);
    boolean verifyUser(String mail, String code);
}
