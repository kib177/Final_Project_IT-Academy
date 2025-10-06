package by.finalproject.itacademy.accountservice.service.exception;

public class UserNotFoundException extends AccountServiceException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
