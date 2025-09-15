package by.finalproject.itacademy.userservice.service.mapper;

import by.finalproject.itacademy.common.dto.PageDTO;
import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PageMapper {

    @Mapping(target = "number", source = "page.number")
    @Mapping(target = "size", source = "page.size")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "first", source = "page.first")
    @Mapping(target = "numberOfElements", source = "page.numberOfElements")
    @Mapping(target = "last", source = "page.last")
    @Mapping(target = "content", expression = "java(mapContent(page.getContent(), contentMapper))")
    PageDTO<Object> toPageOfUser(Page<UserEntity> page, UserMapper userMapper);

    default List<Object> mapContent(List<UserEntity> entities, UserMapper mapper) {
        return entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
