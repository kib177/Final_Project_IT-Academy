package by.finalproject.itacademy.mailservice.service.exception;

public class InvalidVerificationCodeException extends VerificationCodeException {
    public InvalidVerificationCodeException(String message) {
        super(message);
    }
}
