package by.finalproject.itacademy.classifierservice.service.mapper;

import by.finalproject.itacademy.classifierservice.model.dto.OperationCategoryDTO;
import by.finalproject.itacademy.classifierservice.model.entity.OperationCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperationCategoryMapper {

    @Mapping(target = "baseEntity.uuid", source = "uuid")
    @Mapping(target = "baseEntity.dtCreate", source = "dtCreate")
    @Mapping(target = "baseEntity.dtUpdate", source = "dtUpdate")
    @Mapping(target = "title", source = "title")
    OperationCategoryEntity toEntity(OperationCategoryDTO dto);

    @Mapping(source = "baseEntity.uuid", target = "uuid")
    @Mapping(source = "baseEntity.dtCreate", target = "dtCreate")
    @Mapping(source = "baseEntity.dtUpdate", target = "dtUpdate")
    @Mapping(source = "title", target = "title")
    OperationCategoryDTO toDTO(OperationCategoryEntity entity);
}
