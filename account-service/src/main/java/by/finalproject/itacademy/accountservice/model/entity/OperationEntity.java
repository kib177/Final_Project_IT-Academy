package by.finalproject.itacademy.accountservice.model.entity;

import by.finalproject.itacademy.classifierservice.model.entity.CurrencyEntity;
import by.finalproject.itacademy.classifierservice.model.entity.OperationCategoryEntity;
import by.finalproject.itacademy.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private OperationCategoryEntity category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private Double value;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private CurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;
}
