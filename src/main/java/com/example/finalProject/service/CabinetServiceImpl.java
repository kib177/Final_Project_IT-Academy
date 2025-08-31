package com.example.finalProject.service;

import com.example.finalProject.dto.User;
import com.example.finalProject.dto.UserCreate;
import com.example.finalProject.dto.UserRegistration;
import com.example.finalProject.service.api.ICabinetService;

import java.util.UUID;

public class CabinetServiceImpl implements ICabinetService {
    @Override
    public boolean create(UserCreate userCreate) {
        return false;
    }

    @Override
    public boolean registration(UserRegistration userRegistration) {
        return false;
    }

    @Override
    public boolean update(UUID uuid, long dt_update, UserCreate userCreate) {
        return false;
    }

    @Override
    public User get(int page, int size) {
        return null;
    }

    @Override
    public User getByUUID(UUID uuid) {
        return null;
    }
}
