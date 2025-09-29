package by.finalproject.itacademy.common.exception;

public class ExpiredVerificationCodeException extends VerificationCodeException {
    public ExpiredVerificationCodeException(String message) {
        super(message);
    }
}
