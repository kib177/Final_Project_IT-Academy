package com.example.finalProject.storage.mapper;

import com.example.finalProject.dto.User;
import com.example.finalProject.dto.UserCreate;
import com.example.finalProject.dto.UserRegistration;
import com.example.finalProject.dto.enums.UserRole;
import com.example.finalProject.dto.enums.UserStatus;
import com.example.finalProject.storage.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDto(UserEntity entity) {
        return User.builder()
                .uuid(entity.getUuid())
                .dt_create(entity.getDt_create())
                .dt_update(entity.getDt_update())
                .mail(entity.getMail())
                .fio(entity.getFio())
                .role(entity.getRole())
                .status(entity.getStatus())
                .build();
    }

    public UserEntity fromCreateDto(UserCreate dto) {
        return UserEntity.builder()
                .mail(dto.getMail())
                .fio(dto.getFio())
                .role(dto.getRole())
                .status(dto.getStatus())
                .password(dto.getPassword())
                .build();
    }

    public UserEntity fromRegistrationDto(UserRegistration dto) {
        return UserEntity.builder()
                .mail(dto.getMail())
                .fio(dto.getFio())
                .password(dto.getPassword())
                .role(UserRole.USER)
                .status(UserStatus.WAITING_ACTIVATION)
                .build();
    }
}
