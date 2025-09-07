package com.example.finalProject.service.api;

import com.example.finalProject.dto.User;
import com.example.finalProject.dto.UserCreate;
import com.example.finalProject.dto.UserLogin;
import com.example.finalProject.dto.UserRegistration;

import java.util.UUID;

public interface ICabinetService {
    boolean registration(UserRegistration userRegistration);
    String login(UserLogin userLogin);
    boolean update( UUID uuid, long dt_update, UserCreate userCreate);
    //User getByUUID(UUID uuid);

    boolean verifyUser(String mail, String code);
}
