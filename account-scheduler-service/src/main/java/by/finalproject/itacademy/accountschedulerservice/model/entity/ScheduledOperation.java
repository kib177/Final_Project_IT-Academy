package by.finalproject.itacademy.accountschedulerservice.model.entity;

import by.finalproject.itacademy.accountschedulerservice.model.dto.Operation;
import by.finalproject.itacademy.accountschedulerservice.model.dto.Schedule;
import by.finalproject.itacademy.common.model.entity.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.util.Objects;


@Entity
public class ScheduledOperation {

  @EmbeddedId
  private BaseEntity baseEntity;

  private Schedule schedule;

  private Operation operation;

}

