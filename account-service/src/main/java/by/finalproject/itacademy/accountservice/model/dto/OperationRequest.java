package by.finalproject.itacademy.accountservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationRequest {
    private Timestamp date;
    private String description;
    private UUID category;
    private BigDecimal value;
    private UUID currency;
}
