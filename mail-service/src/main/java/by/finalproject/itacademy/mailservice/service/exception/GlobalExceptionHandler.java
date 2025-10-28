package by.finalproject.itacademy.mailservice.service.exception;

import by.finalproject.itacademy.mailservice.model.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<Class<?>, HttpStatus> EXCEPTION_STATUS_MAP = new ConcurrentHashMap<>();

    static {
        EXCEPTION_STATUS_MAP.put(EmailSendingException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(InvalidVerificationCodeException.class, HttpStatus.FORBIDDEN);
        EXCEPTION_STATUS_MAP.put(VerificationCodeException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            EmailSendingException.class,
            InvalidVerificationCodeException.class,
            InvalidVerificationCodeException.class,
            IllegalArgumentException.class,
    })
    public ResponseEntity<ErrorResponse> handleBusinessExceptions(RuntimeException ex, WebRequest request) {
        HttpStatus status = EXCEPTION_STATUS_MAP.getOrDefault(ex.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

        log.warn("Business exception: {} - {}", ex.getClass().getSimpleName(), ex.getMessage());

        return ResponseEntity.status(status).body(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .error(status.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getDescription(false).replace("uri=", ""))
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("Произошла непредвиденная ошибка")
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

