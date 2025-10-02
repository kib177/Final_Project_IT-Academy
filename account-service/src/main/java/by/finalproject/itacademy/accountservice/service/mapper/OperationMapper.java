package by.finalproject.itacademy.accountservice.service.mapper;

import by.finalproject.itacademy.accountservice.model.dto.OperationResponse;
import by.finalproject.itacademy.accountservice.model.dto.OperationRequest;
import by.finalproject.itacademy.accountservice.model.entity.OperationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    OperationResponse toDto(OperationEntity entity);

    OperationEntity toEntity(OperationRequest dto);
}
