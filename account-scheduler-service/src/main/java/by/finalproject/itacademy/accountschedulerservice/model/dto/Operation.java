package by.finalproject.itacademy.accountschedulerservice.model.dto;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.UUID;

@Embeddable
@Data
public class Operation {
  private UUID account;
  private String description;
  private Double value;
  private UUID currency;
  private UUID category;
}


