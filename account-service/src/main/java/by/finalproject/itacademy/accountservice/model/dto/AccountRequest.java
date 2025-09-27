package by.finalproject.itacademy.accountservice.model.dto;

import by.finalproject.itacademy.accountservice.model.enums.AccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String title;
    private String description;
    private BigDecimal balance;
    private AccountTypeEnum type;
    private UUID currency;
}
