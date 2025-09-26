package by.finalproject.itacademy.userservice.model.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class UserLogDTO {
    private UUID uuidUser;
    private String mail;
    private String fio;
    private String role;
}
