package by.finalproject.itacademy.reportservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportParamByDate {
    private List<UUID> accounts;
    private Instant from;
    private Instant to;
    private List<UUID> categories;
}
