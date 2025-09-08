package com.example.finalProject.storage.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "verification")
public class VerificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email
    @Column(nullable = false)
    private String mail;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private long createdAt = Instant.now().toEpochMilli();

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now().toEpochMilli();
      }
}
