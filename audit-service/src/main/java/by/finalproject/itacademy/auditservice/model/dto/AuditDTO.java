package by.finalproject.itacademy.auditservice.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDTO {
    private UUID uuid;
    @JsonProperty("dt_create")
    private Long dtCreate;
    private UserDTO user;
    private String text;
    private String type;
    private String id;
}

