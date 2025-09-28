package by.finalproject.itacademy.userservice.model.dto;


import by.finalproject.itacademy.userservice.model.enums.UserRole;
import by.finalproject.itacademy.userservice.model.enums.UserStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreate{
    private String mail;
    private String fio;
    private String password;
    private UserRole role;
    private UserStatus status;
}
