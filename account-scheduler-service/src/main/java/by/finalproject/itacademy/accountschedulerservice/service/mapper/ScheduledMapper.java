package by.finalproject.itacademy.accountschedulerservice.service.mapper;


import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationRequest;
import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationResponse;
import by.finalproject.itacademy.accountschedulerservice.model.entity.ScheduledOperationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduledMapper {

    ScheduledOperationResponse toDto(ScheduledOperationEntity entity);
    ScheduledOperationEntity toEntity(ScheduledOperationRequest dto);
}
