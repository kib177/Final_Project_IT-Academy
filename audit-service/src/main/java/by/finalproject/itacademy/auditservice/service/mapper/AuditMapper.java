/*
package by.finalproject.itacademy.auditservice.service.mapper;

import by.finalproject.itacademy.auditservice.model.dto.AuditRequest;
import by.finalproject.itacademy.auditservice.model.dto.AuditResponse;
import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.common.jwt.JwtUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditMapper {

    @Mapping(target = "user", ignore = true)
    AuditResponse toAuditDTO(AuditEntity auditEntity);

*/
/*
    @Mapping(target = "dtCreate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "text", source = "audit.userInfo")
    @Mapping(target = "type", source = "audit.type")
    @Mapping(target = "essenceId", source = "audit.essenceId")
    @Mapping(target = "user", expression = "java(toUserDTO(audit.getJwtUser()))")
    AuditEntity toAuditEntity(AuditRequest audit);
*//*

    UserDTO toUserDTO(JwtUser jwtUser);
}
*/
