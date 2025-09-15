package com.example.finalProject.service.api.exception;

public class CabinetException extends RuntimeException {

    public CabinetException() {
        super();
    }

    public CabinetException(String message) {
        super(message);
    }

    public CabinetException(String message, Throwable cause) {
        super(message, cause);
    }

    public CabinetException(Throwable cause) {
        super(cause);
    }
}
