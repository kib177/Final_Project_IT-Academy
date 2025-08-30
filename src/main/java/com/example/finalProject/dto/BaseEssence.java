package com.example.finalProject.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
public class BaseEssence {
    private UUID uuid;
    private long dt_create;
    private long dt_update;

}
