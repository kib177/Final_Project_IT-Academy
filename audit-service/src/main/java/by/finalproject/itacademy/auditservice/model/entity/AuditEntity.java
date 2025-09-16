package by.finalproject.itacademy.auditservice.model.entity;

import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.userservice.model.dto.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "")
    private User user;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    private EssenceTypeEnum type;

    private String id;
}

