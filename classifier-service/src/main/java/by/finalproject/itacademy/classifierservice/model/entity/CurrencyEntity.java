package by.finalproject.itacademy.classifierservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "currencies")
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @CreationTimestamp
    @Column(name = "dt_create", updatable = false, nullable = false)
    private LocalDateTime dtCreate;

    @CreationTimestamp
    @Column(name = "dt_update", nullable = false)
    private LocalDateTime dtUpdate;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;
}
