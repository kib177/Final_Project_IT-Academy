package by.finalproject.itacademy.auditservice.model.entity;

import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "audit")
public class AuditEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID uuid;

    @Column(name = "dt_create", nullable = false)
    private LocalDateTime dtCreate;

    @Embedded
    @Column(nullable = false)
    private UserDTO user;

    @Column(nullable = false, length = 500)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EssenceTypeEnum type;

    @Column(nullable = false)
    private String essenceId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuditEntity entity = (AuditEntity) o;
        return Objects.equals(uuid, entity.uuid)
                && Objects.equals(dtCreate, entity.dtCreate)
                && Objects.equals(user, entity.user)
                && Objects.equals(text, entity.text)
                && type == entity.type
                && Objects.equals(essenceId, entity.essenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, user, text, type, essenceId);
    }

    @Override
    public String toString() {
        return "AuditEntity{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", userUuid='" + user + '\'' +
                ", text='" + text + '\'' +
                ", essenceId='" + essenceId + '\'' +
                '}';
    }
}

