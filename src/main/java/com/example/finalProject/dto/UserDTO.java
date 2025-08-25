package com.example.finalProject.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UserDTO extends BaseEssenceDTO {
    private String mail;
    private String fio;
    private UserRole role;
    private UserStatus status;

}
