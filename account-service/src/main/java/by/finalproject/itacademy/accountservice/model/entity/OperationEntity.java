package by.finalproject.itacademy.accountservice.model.entity;

import by.finalproject.itacademy.classifierservice.model.entity.CurrencyEntity;
import by.finalproject.itacademy.classifierservice.model.entity.OperationCategoryEntity;
import by.finalproject.itacademy.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operations")
public class OperationEntity {

    @EmbeddedId
    private BaseEntity baseEntity;

    @Column(nullable = false)
    private Instant date;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private OperationCategoryEntity category;

    @Column(nullable = false)
    private Double value;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private CurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;
}
