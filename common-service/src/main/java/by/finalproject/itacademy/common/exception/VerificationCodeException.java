package by.finalproject.itacademy.common.exception;

public class VerificationCodeException extends UserServiceException {
    public VerificationCodeException(String message) {
        super(message);
    }

    public VerificationCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
