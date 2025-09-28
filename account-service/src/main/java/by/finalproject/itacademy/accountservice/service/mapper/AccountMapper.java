package by.finalproject.itacademy.accountservice.service.mapper;

import by.finalproject.itacademy.accountservice.model.dto.AccountResponse;
import by.finalproject.itacademy.accountservice.model.dto.AccountRequest;
import by.finalproject.itacademy.accountservice.model.entity.AccountEntity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponse toDto(AccountEntity entity);
    AccountEntity toEntity(AccountRequest dto);


}
