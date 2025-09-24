package by.finalproject.itacademy.accountservice.model.entity;

import by.finalproject.itacademy.accountservice.model.enums.AccountTypeEnum;
import by.finalproject.itacademy.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @CreationTimestamp
    @Column(name = "dt_create", updatable = false, nullable = false)
    private LocalDateTime dtCreate;

    @CreationTimestamp
    @Column(name = "dt_update", nullable = false)
    private LocalDateTime dtUpdate;

    private String title;
    private String description;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountTypeEnum type;

    private UUID currency;

}

