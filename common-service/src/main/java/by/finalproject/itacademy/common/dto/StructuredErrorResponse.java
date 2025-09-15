package by.finalproject.itacademy.common.dto;

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
    private List<FieldError> errors;

    @Data
    public static class FieldError {
        private String field;
        private String message;
    }
}
