package by.finalproject.itacademy.accountschedulerservice.model.entity;

import by.finalproject.itacademy.accountschedulerservice.model.dto.Operation;
import by.finalproject.itacademy.accountschedulerservice.model.dto.Schedule;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scheduler")
public class ScheduledOperationEntity {
    @Id
    @GeneratedValue
    private UUID uuid;

    private Timestamp dtCreate;
    private Timestamp dtUpdate;

    @Embedded
    private Schedule schedule;

    @Embedded
    private Operation operation;
}



