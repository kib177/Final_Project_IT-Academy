package by.finalproject.itacademy.auditservice.service.mapper;

import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import by.finalproject.itacademy.userservice.model.entity.UserEntity;

public class Mapper {

    public static UserDTO toUserDTO(UserEntity user) {
        return UserDTO.builder()
                .uuid(user.getUuid())
                .fio(user.getFio())
                .role(String.valueOf(user.getRole()))
                .mail(user.getMail())
                .build();
    }
}
