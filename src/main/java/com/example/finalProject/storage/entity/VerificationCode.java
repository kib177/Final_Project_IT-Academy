package com.example.finalProject.storage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "verification")
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String mail;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private long createdAt = Instant.now().toEpochMilli();
}
