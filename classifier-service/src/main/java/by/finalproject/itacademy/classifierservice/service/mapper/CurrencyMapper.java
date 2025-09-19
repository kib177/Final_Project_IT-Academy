package by.finalproject.itacademy.classifierservice.service.mapper;

import by.finalproject.itacademy.classifierservice.model.dto.CurrencyDTO;
import by.finalproject.itacademy.classifierservice.model.entity.CurrencyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    @Mapping(target = "baseEntity.uuid", source = "uuid")
    @Mapping(target = "baseEntity.dtCreate", source = "dtCreate")
    @Mapping(target = "baseEntity.dtUpdate", source = "dtUpdate")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    CurrencyEntity toEntity(CurrencyDTO dto);

    @Mapping(source = "baseEntity.uuid", target = "uuid")
    @Mapping(source = "baseEntity.dtCreate", target = "dtCreate")
    @Mapping(source = "baseEntity.dtUpdate", target = "dtUpdate")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    CurrencyDTO toDTO(CurrencyEntity entity);
}