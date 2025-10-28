package by.finalproject.itacademy.auditservice.service.exception;

public class EntityAlreadyExistsException extends AuditServiceException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
