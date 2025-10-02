package by.finalproject.itacademy.userservice.service.exception;

public class VerificationCodeException extends UserServiceException {
    public VerificationCodeException(String message) {
        super(message);
    }

    public VerificationCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
