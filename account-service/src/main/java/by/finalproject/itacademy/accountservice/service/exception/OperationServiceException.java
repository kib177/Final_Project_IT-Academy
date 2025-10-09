package by.finalproject.itacademy.accountservice.service.exception;

public class OperationServiceException extends RuntimeException {
    public OperationServiceException(String message) {
        super(message);
    }

    public OperationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}