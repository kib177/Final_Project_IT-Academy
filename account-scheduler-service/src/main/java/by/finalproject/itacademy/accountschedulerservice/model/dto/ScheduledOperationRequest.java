package by.finalproject.itacademy.accountschedulerservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduledOperationRequest {
    private Schedule schedule;
    private Operation operation;
}