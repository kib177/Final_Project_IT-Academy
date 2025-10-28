package by.finalproject.itacademy.reportservice.model.dto;

import by.finalproject.itacademy.reportservice.model.enums.ReportStatusEnum;
import by.finalproject.itacademy.reportservice.model.enums.ReportTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponse {
    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private ReportStatusEnum status;
    private ReportTypeEnum type;
    private String description;
    private Object params;
}
