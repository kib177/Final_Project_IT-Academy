package by.finalproject.itacademy.accountservice.model.dto;

import by.finalproject.itacademy.accountservice.model.enums.AccountTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AccountDTO {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private AccountTypeEnum type;

    @NotNull
    private UUID currency;
}
