package by.finalproject.itacademy.accountschedulerservice.service.exception;

public class UpdateOperationException extends RuntimeException {
    public UpdateOperationException(String message) {
        super(message);
    }

    public UpdateOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
