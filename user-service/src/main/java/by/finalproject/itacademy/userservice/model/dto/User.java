package by.finalproject.itacademy.userservice.model.dto;

import by.finalproject.itacademy.userservice.model.enums.UserStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private String mail;
    private String fio;
    private String role;
    private UserStatus status;

}
