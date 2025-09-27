package by.finalproject.itacademy.classifierservice.service.mapper;



import by.finalproject.itacademy.classifierservice.model.dto.CurrencyRequest;
import by.finalproject.itacademy.classifierservice.model.dto.CurrencyResponse;
import by.finalproject.itacademy.classifierservice.model.dto.PageOfCurrency;
import by.finalproject.itacademy.classifierservice.model.entity.CurrencyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyEntity toEntity(CurrencyRequest dto);
    CurrencyResponse toDTO(CurrencyEntity entity);

    @Mapping(target = "number", source = "page.number")
    @Mapping(target = "size", source = "page.size")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "first", source = "page.first")
    @Mapping(target = "numberOfElements", source = "page.numberOfElements")
    @Mapping(target = "last", source = "page.last")
    @Mapping(target = "content", expression = "java(map(page.getContent(), contentMapper))")
    PageOfCurrency toPageOfCurrency(Page<CurrencyEntity> page, CurrencyMapper contentMapper);

    default List<CurrencyResponse> map(List<CurrencyEntity> entities,CurrencyMapper mapper) {
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}