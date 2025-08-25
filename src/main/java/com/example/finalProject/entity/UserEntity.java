package com.example.finalProject.entity;

import com.example.finalProject.dto.UserRole;
import com.example.finalProject.dto.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID uuid;

    @Column(name = "dt_create", nullable = false, updatable = false)
    private LocalDateTime dt_create;

    @Column(name = "dt_update", nullable = false)
    private LocalDateTime dt_update;

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
        dt_create = LocalDateTime.now();
        dt_update = dt_create;
    }

    @PreUpdate
    protected void onUpdate() {
        dt_update = LocalDateTime.now();
    }
}
