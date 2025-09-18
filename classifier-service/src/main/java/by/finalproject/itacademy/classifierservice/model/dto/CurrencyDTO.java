package by.finalproject.itacademy.classifierservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDTO {
    private UUID uuid;
    private long dtCreate;
    private long dtUpdate;
    private String title;
    private String description;
}
