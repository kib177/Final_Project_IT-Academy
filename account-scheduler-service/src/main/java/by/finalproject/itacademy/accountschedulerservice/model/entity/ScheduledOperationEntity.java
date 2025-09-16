package by.finalproject.itacademy.accountschedulerservice.model.entity;


import by.finalproject.itacademy.accountschedulerservice.model.dto.Operation;
import by.finalproject.itacademy.accountschedulerservice.model.dto.Schedule;
import by.finalproject.itacademy.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scheduler")
public class ScheduledOperationEntity {

  @EmbeddedId
  private BaseEntity baseEntity;

  @NotBlank

  private Schedule schedule;

  @NotBlank
  private Operation operation;

}

