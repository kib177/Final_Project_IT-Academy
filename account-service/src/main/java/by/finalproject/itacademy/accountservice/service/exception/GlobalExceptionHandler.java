package by.finalproject.itacademy.accountservice.service.exception;


import by.finalproject.itacademy.accountservice.model.exceptionDto.ErrorResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.security.auth.login.AccountException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    private static final Map<Class<?>, HttpStatus> EXCEPTION_STATUS_MAP = new ConcurrentHashMap<>();

    static {
        EXCEPTION_STATUS_MAP.put(AccountNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS_MAP.put(AccountException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(ClassifierNotFoundException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(OperationNotFoundException.class, HttpStatus.NOT_FOUND);
        EXCEPTION_STATUS_MAP.put(OperationServiceException.class, HttpStatus.INTERNAL_SERVER_ERROR);
        EXCEPTION_STATUS_MAP.put(InvalidOperationDataException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
        EXCEPTION_STATUS_MAP.put(FeignClientException.class, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            AccountNotFoundException.class,
            AccountServiceException.class,
            ClassifierNotFoundException.class,
            OperationNotFoundException.class,
            OperationServiceException.class,
            InvalidOperationDataException.class,
            IllegalArgumentException.class,
            FeignClientException.class
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

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException ex, WebRequest request) {
        log.error("Feign communication error: {}", ex.getMessage(), ex);

        HttpStatus status;
        String message;

        if (ex.status() == 404) {
            status = HttpStatus.NOT_FOUND;
            message = "Запрашиваемый ресурс не найден во внешнем сервисе";
        } else if (ex.status() >= 400 && ex.status() < 500) {
            status = HttpStatus.BAD_REQUEST;
            message = "Некорректный запрос к внешнему сервису";
        } else {
            status = HttpStatus.SERVICE_UNAVAILABLE;
            message = "Внешний сервис временно недоступен";
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponse, status);
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
