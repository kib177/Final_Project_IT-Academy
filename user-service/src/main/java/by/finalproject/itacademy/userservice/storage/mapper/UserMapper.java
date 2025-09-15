package by.finalproject.itacademy.userservice.storage.mapper;


import by.finalproject.itacademy.userservice.dto.User;
import by.finalproject.itacademy.userservice.dto.UserCreate;
import by.finalproject.itacademy.userservice.dto.UserRegistration;
import by.finalproject.itacademy.userservice.storage.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDto(UserEntity entity);
    UserEntity toEntity(User dto);
    UserEntity fromRegistrationDto(UserRegistration dto);
    UserEntity fromCreateDto(UserCreate dto);
}
