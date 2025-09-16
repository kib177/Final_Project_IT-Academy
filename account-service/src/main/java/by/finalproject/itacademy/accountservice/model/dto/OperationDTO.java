package by.finalproject.itacademy.accountservice.model.dto;

import jakarta.validation.constraints.DecimalMin;
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
public class OperationDTO {
    @NotNull
    private Long date;

    private String description;

    @NotNull
    private UUID category;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal value;

    @NotNull
    private UUID currency;
}
