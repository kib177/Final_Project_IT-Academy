package by.finalproject.itacademy.classifierservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationCategoryDTO {
    private UUID uuid;
    private Instant dtCreate;
    private Instant dtUpdate;
    private String title;
}
