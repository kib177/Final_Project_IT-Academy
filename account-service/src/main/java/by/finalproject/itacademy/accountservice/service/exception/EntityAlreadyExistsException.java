package by.finalproject.itacademy.accountservice.service.exception;

public class EntityAlreadyExistsException extends AccountServiceException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
