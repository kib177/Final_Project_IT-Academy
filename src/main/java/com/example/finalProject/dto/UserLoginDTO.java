package com.example.finalProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserLoginDTO {
    private String mail;
    private String password;
}
