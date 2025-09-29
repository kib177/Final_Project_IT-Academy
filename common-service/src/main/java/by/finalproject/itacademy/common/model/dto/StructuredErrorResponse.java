package by.finalproject.itacademy.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StructuredErrorResponse {
    private String logref;
    private List<ErrorResponse> errors;

}
