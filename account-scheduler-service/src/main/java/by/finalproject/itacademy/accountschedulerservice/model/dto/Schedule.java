package by.finalproject.itacademy.accountschedulerservice.model.dto;

import by.finalproject.itacademy.accountschedulerservice.model.enums.TimeUnitEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule {

  private Long startTime;

  private Long stopTime;

  private Long interval;

  private TimeUnitEnum timeUnit;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Schedule schedule = (Schedule) o;
    return Objects.equals(this.startTime, schedule.startTime) &&
        Objects.equals(this.stopTime, schedule.stopTime) &&
        Objects.equals(this.interval, schedule.interval) &&
        Objects.equals(this.timeUnit, schedule.timeUnit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startTime, stopTime, interval, timeUnit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Schedule {\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    stopTime: ").append(toIndentedString(stopTime)).append("\n");
    sb.append("    interval: ").append(toIndentedString(interval)).append("\n");
    sb.append("    timeUnit: ").append(toIndentedString(timeUnit)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

