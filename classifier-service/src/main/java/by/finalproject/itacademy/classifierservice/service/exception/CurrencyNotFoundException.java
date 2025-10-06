package by.finalproject.itacademy.classifierservice.service.exception;

public class CurrencyNotFoundException extends AccountServiceException {
    public CurrencyNotFoundException(String message) {
        super(message);
    }
}
