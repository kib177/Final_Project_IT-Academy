package by.finalproject.itacademy.userservice.service.exception;

public class FeignClientException extends RuntimeException {
    public FeignClientException(String message) {
        super(message);
    }

    public FeignClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
