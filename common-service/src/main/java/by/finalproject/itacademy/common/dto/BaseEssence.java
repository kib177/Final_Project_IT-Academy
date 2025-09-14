package by.finalproject.itacademy.common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class BaseEssence {
    private UUID uuid;
    private Long dtCreate;
    private Long dtUpdate;
}
