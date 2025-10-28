package by.finalproject.itacademy.accountservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operations")
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @CreationTimestamp
    @Column(name = "dt_create", updatable = false, nullable = false)
    private LocalDateTime dtCreate;

    @CreationTimestamp
    @Column(name = "dt_update", nullable = false)
    private LocalDateTime dtUpdate;

    @JoinColumn(name = "category_id", nullable = false)
    private UUID category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private BigDecimal value;

    @JoinColumn(name = "currency_id", nullable = false)
    private UUID currency;

    @JoinColumn(name = "account_id", nullable = false)
    private UUID account;
}
