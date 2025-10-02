package by.finalproject.itacademy.reportservice.service.mapper;

import by.finalproject.itacademy.reportservice.model.dto.PageOfReport;
import by.finalproject.itacademy.reportservice.model.dto.ReportResponse;
import by.finalproject.itacademy.reportservice.model.entity.ReportEntity;
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
    PageOfReport toPageOfUser(Page<ReportEntity> page, ReportMapper contentMapper);

    default List<ReportResponse> mapContent(List<ReportEntity> entities, ReportMapper mapper) {
        return entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
