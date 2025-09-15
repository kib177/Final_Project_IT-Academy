package by.finalproject.itacademy.userservice.service.mapper;


import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserCreate;
import by.finalproject.itacademy.userservice.model.dto.UserRegistration;
import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDto(UserEntity entity);
    UserEntity toEntity(User dto);
    UserEntity fromRegistrationDto(UserRegistration dto);
    UserEntity fromCreateDto(UserCreate dto);
}
