package by.finalproject.itacademy.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {
    @Email
    @NotBlank
    private String mail;
    @NotBlank
    private String password;
}
