package by.finalproject.itacademy.common.exception;

import by.finalproject.itacademy.common.model.dto.ErrorResponse;
import by.finalproject.itacademy.common.model.dto.FieldError;
import by.finalproject.itacademy.common.model.dto.StructuredErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(StructuredValidationException.class)
    public ResponseEntity<Object> handleStructuredValidation(StructuredValidationException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("logref", "structured_error");
        body.put("errors", ex.getErrors());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            BadRequestException.class,
            UnauthorizedException.class,
            ForbiddenException.class,
            InternalServerErrorException.class
    })
    public ResponseEntity<Object> handleApiBaseExceptions(ApiBaseException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("logref", "error");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.resolve(getHttpStatus(ex).value()));
    }

    private HttpStatus getHttpStatus(ApiBaseException ex) {
        ResponseStatus status = ex.getClass().getAnnotation(ResponseStatus.class);
        return status != null ? status.value() : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFound(AccountNotFoundException ex) {
        log.warn("Счет не найден: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(OperationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOperationNotFound(OperationNotFoundException ex) {
        log.warn("Операция не найдена: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataVersionException.class)
    public ResponseEntity<ErrorResponse> handleDataVersion(DataVersionException ex) {
        log.warn("Конфликт версий данных: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex) {
        log.warn("Недостаточно средств: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        log.warn("Ошибка валидации: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StructuredErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("Ошибка валидации данных: {}", ex.getMessage());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        StructuredErrorResponse error = new StructuredErrorResponse("structured_error", fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Внутренняя ошибка сервера: ", ex);
        ErrorResponse error = new ErrorResponse("error", "Внутренняя ошибка сервера");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

