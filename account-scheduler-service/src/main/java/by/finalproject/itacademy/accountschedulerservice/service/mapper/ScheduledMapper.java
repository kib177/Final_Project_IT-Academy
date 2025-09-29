package by.finalproject.itacademy.accountschedulerservice.service.mapper;


import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationRequest;
import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationResponse;
import by.finalproject.itacademy.accountschedulerservice.model.entity.ScheduledOperationEntity;
import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserCreate;
import by.finalproject.itacademy.userservice.model.dto.UserRegistration;
import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduledMapper {

    ScheduledOperationResponse toDto(ScheduledOperationEntity entity);
    ScheduledOperationEntity toEntity(ScheduledOperationRequest dto);
}
