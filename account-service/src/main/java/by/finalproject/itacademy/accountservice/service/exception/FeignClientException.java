package by.finalproject.itacademy.accountservice.service.exception;

public class FeignClientException extends RuntimeException {
    public FeignClientException(String message) {
        super(message);
    }

    public FeignClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
