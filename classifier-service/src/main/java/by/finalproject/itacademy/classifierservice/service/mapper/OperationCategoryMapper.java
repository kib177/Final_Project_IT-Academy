package by.finalproject.itacademy.classifierservice.service.mapper;

import by.finalproject.itacademy.classifierservice.model.dto.OperationCategoryDTO;
import by.finalproject.itacademy.classifierservice.model.dto.PageOfOperationCategory;
import by.finalproject.itacademy.classifierservice.model.entity.OperationCategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OperationCategoryMapper {
    OperationCategoryEntity toEntity(OperationCategoryDTO dto);
    OperationCategoryDTO toDTO(OperationCategoryEntity entity);

    @Mapping(target = "number", source = "page.number")
    @Mapping(target = "size", source = "page.size")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "first", source = "page.first")
    @Mapping(target = "numberOfElements", source = "page.numberOfElements")
    @Mapping(target = "last", source = "page.last")
    @Mapping(target = "content", expression = "java(mapOperationContent(page.getContent(), contentMapper))")
    PageOfOperationCategory toPageOfOperationCategory(Page<OperationCategoryEntity> page, OperationCategoryMapper contentMapper);

    default List<OperationCategoryDTO> mapOperationContent(List<OperationCategoryEntity> entities, OperationCategoryMapper mapper) {
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
