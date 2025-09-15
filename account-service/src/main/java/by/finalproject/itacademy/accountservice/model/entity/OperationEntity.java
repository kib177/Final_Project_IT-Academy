package by.finalproject.itacademy.accountservice.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder
@Table(name = "operations")
public class OperationEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID uuid;

    private Long dtCreate;
    private Long dtUpdate;

    private Long date;
    private String description;
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private OperationCategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private CurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;
}
