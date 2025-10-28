package by.finalproject.itacademy.accountschedulerservice.service.exception;

public class ScheduledOperationServiceException extends RuntimeException {
    public ScheduledOperationServiceException(String message) {
        super(message);
    }

    public ScheduledOperationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}