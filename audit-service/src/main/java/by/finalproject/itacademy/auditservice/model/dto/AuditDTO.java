package by.finalproject.itacademy.auditservice.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDTO {
    private UUID uuid;
    private Timestamp dtCreate;
    private UserDTO user;
    private String text;
    private String type;
    private String id;
}

