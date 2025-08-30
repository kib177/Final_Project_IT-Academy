package com.example.finalProject.dto;

import com.example.finalProject.dto.enums.UserRole;
import com.example.finalProject.dto.enums.UserStatus;
import lombok.*;

@Data
@Builder
public class UserCreate{
    private String mail;
    private String fio;
    private String password;
    private UserRole role;
    private UserStatus status;

}
