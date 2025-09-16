package by.finalproject.itacademy.accountservice.model.entity;

import by.finalproject.itacademy.accountservice.model.enums.AccountTypeEnum;
import by.finalproject.itacademy.common.model.entity.BaseEntity;
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

    @EmbeddedId
    private BaseEntity baseEntity;

    private String title;
    private String description;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountTypeEnum type;

    private UUID currency;

}

