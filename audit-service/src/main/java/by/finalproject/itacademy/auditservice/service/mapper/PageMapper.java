/*
package by.finalproject.itacademy.auditservice.service.mapper;

import by.finalproject.itacademy.auditservice.model.dto.AuditResponse;
import by.finalproject.itacademy.auditservice.model.dto.PageOfAudit;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
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
    PageOfAudit toPageOfAudit(Page<AuditEntity> page, AuditMapper contentMapper);

    default List<AuditResponse> mapContent(List<AuditEntity> entities, AuditMapper mapper) {
        return entities.stream()
                .map(mapper::toAuditDTO)
                .collect(Collectors.toList());
    }
}
*/
