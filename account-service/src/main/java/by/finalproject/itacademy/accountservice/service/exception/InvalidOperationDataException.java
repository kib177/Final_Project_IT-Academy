package by.finalproject.itacademy.accountservice.service.exception;

public class InvalidOperationDataException extends RuntimeException {
    public InvalidOperationDataException(String message) {
        super(message);
    }

    public InvalidOperationDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
