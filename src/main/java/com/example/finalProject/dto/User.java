package com.example.finalProject.dto;


import com.example.finalProject.dto.enums.UserRole;
import com.example.finalProject.dto.enums.UserStatus;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class User extends BaseEssence {
    private String mail;
    private String fio;
    private UserRole role;
    private UserStatus status;

}
