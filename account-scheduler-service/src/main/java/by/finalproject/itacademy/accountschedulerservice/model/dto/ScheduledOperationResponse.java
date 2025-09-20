package by.finalproject.itacademy.accountschedulerservice.model.dto;

import java.util.UUID;

public class ScheduledOperationResponse {
    private UUID uuid;
    private Long dtCreate;
    private Long dtUpdate;
    private Schedule schedule;
    private Operation operation;
}
