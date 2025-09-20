package by.finalproject.itacademy.auditservice.model.entity;

import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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

    @Column(name = "dt_create", nullable = false)
    private Timestamp dtCreate;

    @Column(nullable = false)
    private String userUuid;

    @Column(nullable = false, length = 500)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EssenceTypeEnum type;

    @Column(nullable = false)
    private String essenceId;
}

