package by.finalproject.itacademy.userservice.model.entity;

import by.finalproject.itacademy.userservice.model.enums.UserRole;
import by.finalproject.itacademy.userservice.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Email;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "dt_create", updatable = false, nullable = false)
    private Timestamp dtCreate;

    @Column(name = "dt_update", nullable = false)
    private Timestamp dtUpdate;

    @Email
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "fio", nullable = false)
    private String fio;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    @PrePersist
    protected void onCreate() {

        if (role == null) {
            role = UserRole.USER;
        }
        if (status == null) {
            status = UserStatus.WAITING_ACTIVATION;
        }
    }
}
