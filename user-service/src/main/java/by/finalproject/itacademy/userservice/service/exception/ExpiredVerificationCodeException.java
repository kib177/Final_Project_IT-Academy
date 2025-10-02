package by.finalproject.itacademy.userservice.service.exception;

public class ExpiredVerificationCodeException extends VerificationCodeException {
    public ExpiredVerificationCodeException(String message) {
        super(message);
    }
}
