package by.finalproject.itacademy.accountservice.service.mapper;

import by.finalproject.itacademy.accountservice.model.dto.AccountResponse;
import by.finalproject.itacademy.accountservice.model.dto.PageOfAccount;
import by.finalproject.itacademy.accountservice.model.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AccountPageMapper {

    @Mapping(target = "number", source = "page.number")
    @Mapping(target = "size", source = "page.size")
    @Mapping(target = "totalPages", source = "page.totalPages")
    @Mapping(target = "totalElements", source = "page.totalElements")
    @Mapping(target = "first", source = "page.first")
    @Mapping(target = "numberOfElements", source = "page.numberOfElements")
    @Mapping(target = "last", source = "page.last")
    @Mapping(target = "content", expression = "java(mapContent(page.getContent(), contentMapper))")
    PageOfAccount toPageOfUser(Page<AccountEntity> page, AccountMapper contentMapper);

    default List<AccountResponse> mapContent(List<AccountEntity> entities, AccountMapper mapper) {
        return entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
