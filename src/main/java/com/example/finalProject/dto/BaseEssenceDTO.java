package com.example.finalProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class BaseEssenceDTO {
    private UUID uuid;
    private Timestamp dt_create;
    private Timestamp dt_update;

}
