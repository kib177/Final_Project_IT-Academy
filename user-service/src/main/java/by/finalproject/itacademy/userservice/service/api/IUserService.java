package by.finalproject.itacademy.userservice.service.api;


import by.finalproject.itacademy.userservice.model.dto.PageOfUser;
import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserCreate;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    @Transactional
    boolean create(UserCreate userCreate);

    User getById(UUID uuid);
    PageOfUser getUsersPage(Pageable pageable);

    @Transactional
    void updateUser(UUID uuid, Long dtUpdate, UserCreate userCreate);
}
