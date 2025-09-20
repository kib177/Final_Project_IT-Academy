package by.finalproject.itacademy.accountschedulerservice.model.dto;

import by.finalproject.itacademy.accountschedulerservice.model.enums.TimeUnitEnum;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Embeddable
@Data
public class Schedule {
  private Long startTime;
  private Long stopTime;
  private Long interval;

  @Enumerated(EnumType.STRING)
  private TimeUnitEnum timeUnit;
}


