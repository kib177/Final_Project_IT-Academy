package by.finalproject.itacademy.accountservice.model.dto;

import by.finalproject.itacademy.accountservice.model.enums.AccountTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AccountDTO {

    @NotBlank
    private String title;

    private String description;
    private BigDecimal balance;
    @NotNull
    private AccountTypeEnum type;

    @NotNull
    private UUID currency;
}
