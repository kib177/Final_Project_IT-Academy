package by.finalproject.itacademy.common.exception;

import by.finalproject.itacademy.common.model.dto.StructuredErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StructuredValidationException extends ApiBaseException {
    private final StructuredErrorResponse errors;

    public StructuredValidationException(String message, StructuredErrorResponse errors) {
        super(message);
        this.errors = errors;
    }

}
