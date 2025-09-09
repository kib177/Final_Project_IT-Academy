package com.example.finalProject.dto;

import com.example.finalProject.dto.enums.UserRole;
import com.example.finalProject.dto.enums.UserStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreate{
    private UUID uuid;
    private long dt_create;
    private long dt_update;
    private String mail;
    private String fio;
    private String password;
    private UserRole role;
    private UserStatus status;

}
