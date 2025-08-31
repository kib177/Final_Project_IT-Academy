package com.example.finalProject.service.api;

import com.example.finalProject.dto.User;
import com.example.finalProject.dto.UserCreate;
import com.example.finalProject.dto.UserRegistration;

import java.util.UUID;

public interface ICabinetService {
    boolean create(UserCreate userCreate);
    boolean registration(UserRegistration userRegistration);
    boolean update( UUID uuid, long dt_update, UserCreate userCreate);
    User get(int page, int size);
    User getByUUID(UUID uuid);
}
