package by.finalproject.itacademy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEssence {
    private UUID uuid;
    private Long dtCreate;
    private Long dtUpdate;
}
