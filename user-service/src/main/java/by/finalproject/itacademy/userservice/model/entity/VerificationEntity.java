package by.finalproject.itacademy.userservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;

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
    private Instant createdAt =now();

    @PrePersist
    protected void onCreate() {
        createdAt =now();
      }
}
