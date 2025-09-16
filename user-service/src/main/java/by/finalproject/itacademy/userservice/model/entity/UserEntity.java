package by.finalproject.itacademy.userservice.model.entity;


import by.finalproject.itacademy.common.model.entity.BaseEntity;
import by.finalproject.itacademy.userservice.model.enums.UserRole;
import by.finalproject.itacademy.userservice.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Email;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class UserEntity {

    @EmbeddedId
    private BaseEntity baseEntity;
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

        dtCreate = now();
        dtUpdate = dtCreate;
        if (role == null) {
            role = UserRole.USER;
        }
        if (status == null) {
            status = UserStatus.WAITING_ACTIVATION;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        dtUpdate = now();
    }
}
