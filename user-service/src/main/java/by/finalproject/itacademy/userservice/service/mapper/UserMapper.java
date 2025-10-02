package by.finalproject.itacademy.userservice.service.mapper;


import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserCreate;
import by.finalproject.itacademy.userservice.model.dto.UserRegistration;
import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "dtCreate" , source = "dtCreate")
    @Mapping(target = "dtUpdate" , source = "dtUpdate")
    User toDto(UserEntity entity);
    UserEntity toEntity(User dto);
    UserEntity fromRegistrationDto(UserRegistration dto);
    UserEntity fromCreateDto(UserCreate dto);
}
