package by.finalproject.itacademy.accountschedulerservice.model.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

  private UUID account;

  private String description;

  private BigDecimal value;

  private UUID currency;

  private UUID category;

  public Operation account(UUID account) {
    this.account = account;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Operation operation = (Operation) o;
    return Objects.equals(this.account, operation.account) &&
        Objects.equals(this.description, operation.description) &&
        Objects.equals(this.value, operation.value) &&
        Objects.equals(this.currency, operation.currency) &&
        Objects.equals(this.category, operation.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(account, description, value, currency, category);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Operation {\n");
    sb.append("    account: ").append(toIndentedString(account)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
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

