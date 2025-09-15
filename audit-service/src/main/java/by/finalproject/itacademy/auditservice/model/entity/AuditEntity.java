package by.finalproject.itacademy.auditservice.model.entity;

import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "audit")
public class AuditEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID uuid;

    @Column(name = "dt_create")
    private Instant dtCreate;

    @Column(name = "user_uuid")
    private UUID userUuid;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    private EssenceTypeEnum type;

    private String id;
}

