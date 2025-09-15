package by.finalproject.itacademy.userservice.storage.entity;


import by.finalproject.itacademy.userservice.dto.enums.UserRole;
import by.finalproject.itacademy.userservice.dto.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Email;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID uuid;

    @Column(name = "dt_create", nullable = false, updatable = false)
    private long dt_create;

    @Column(name = "dt_update", nullable = false)
    private long dt_update;

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

        dt_create = Instant.now().toEpochMilli();
        dt_update = dt_create;
        if(role == null) {role = UserRole.USER;}
        if(status == null) {status = UserStatus.WAITING_ACTIVATION;}
    }

    @PreUpdate
    protected void onUpdate() {
        dt_update = Instant.now().toEpochMilli();
    }
}
