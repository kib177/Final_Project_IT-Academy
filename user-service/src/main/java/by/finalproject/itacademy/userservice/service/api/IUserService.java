package by.finalproject.itacademy.userservice.service.api;


import by.finalproject.itacademy.common.dto.PageDTO;
import by.finalproject.itacademy.userservice.dto.User;
import by.finalproject.itacademy.userservice.dto.UserCreate;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    boolean create(UserCreate userCreate);
    Optional<User> getById(UUID uuid);
    PageDTO<Object> getUsersPage(Pageable pageable);
}
