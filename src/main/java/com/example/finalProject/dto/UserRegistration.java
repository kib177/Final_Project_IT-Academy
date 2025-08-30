package com.example.finalProject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
public class UserRegistration {
    @Email
    @NotBlank
    private String mail;
    @NotBlank
    private String fio;
    @NotBlank
    private String password;
}
