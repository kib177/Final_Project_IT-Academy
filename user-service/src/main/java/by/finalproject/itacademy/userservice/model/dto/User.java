package by.finalproject.itacademy.userservice.model.dto;


import by.finalproject.itacademy.userservice.model.enums.UserRole;
import by.finalproject.itacademy.userservice.model.enums.UserStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID uuid;
    private long dt_create;
    private long dt_update;
    private String mail;
    private String fio;
    private String role;
    private UserStatus status;

}
