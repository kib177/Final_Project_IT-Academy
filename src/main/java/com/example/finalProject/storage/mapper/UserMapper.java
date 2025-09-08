package com.example.finalProject.storage.mapper;

import com.example.finalProject.dto.User;
import com.example.finalProject.dto.UserCreate;
import com.example.finalProject.dto.UserRegistration;
import com.example.finalProject.storage.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDto(UserEntity entity);
    UserEntity toEntity(User dto);
    UserEntity fromRegistrationDto(UserRegistration dto);
    UserEntity fromCreateDto(UserCreate dto);
}
