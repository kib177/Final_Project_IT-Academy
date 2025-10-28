package by.finalproject.itacademy.reportservice.model.entity;


import by.finalproject.itacademy.reportservice.model.enums.ReportStatusEnum;
import by.finalproject.itacademy.reportservice.model.enums.ReportTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report")
public class ReportEntity {

    @Id
    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID uuid;

    @Column(nullable = false)
    private LocalDateTime dtCreate;

    @Column(nullable = false)
    private LocalDateTime dtUpdate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReportStatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false, length = 20)
    private ReportTypeEnum type;

    private String description;

   // private Object params;

}
