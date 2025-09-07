package com.example.finalProject.service.api;

import com.example.finalProject.dto.PageOfUser;
import com.example.finalProject.dto.User;
import com.example.finalProject.dto.UserCreate;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    boolean create(UserCreate userCreate);
    Optional<User> getById(UUID uuid);
    PageOfUser<Object> getUsersPage(Pageable pageable);
}
