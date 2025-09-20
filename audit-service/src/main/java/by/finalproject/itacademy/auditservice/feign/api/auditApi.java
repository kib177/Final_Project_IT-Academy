package by.finalproject.itacademy.auditservice.feign.api;

import by.finalproject.itacademy.auditservice.model.dto.UserDTO;

import java.util.UUID;

public interface auditApi {
    UserDTO getUser(UUID userUuid);
}
