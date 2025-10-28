package by.finalproject.itacademy.accountschedulerservice.service.mapper;

import by.finalproject.itacademy.accountschedulerservice.model.dto.PageOfScheduledOperation;
import by.finalproject.itacademy.accountschedulerservice.model.dto.ScheduledOperationResponse;
import by.finalproject.itacademy.accountschedulerservice.model.entity.ScheduledOperationEntity;
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
    PageOfScheduledOperation toPageOfUser(Page<ScheduledOperationEntity> page, ScheduledMapper contentMapper);

    default List<ScheduledOperationResponse> mapContent(List<ScheduledOperationEntity> entities, ScheduledMapper mapper) {
        return entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
