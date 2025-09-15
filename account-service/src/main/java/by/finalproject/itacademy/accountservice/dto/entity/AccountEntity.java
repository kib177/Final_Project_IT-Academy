package by.finalproject.itacademy.accountservice.dto.entity;

import by.finalproject.itacademy.userservice.storage.entity.UserEntity;
import by.finalproject.itacademy.accountservice.dto.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID uuid;

    private Long dtCreate;
    private Long dtUpdate;

    private String title;
    private String description;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private CurrencyEntity currency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

