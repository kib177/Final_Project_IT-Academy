package by.finalproject.itacademy.classifierservice.service.exception;

public class UserNotFoundException extends AccountServiceException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
