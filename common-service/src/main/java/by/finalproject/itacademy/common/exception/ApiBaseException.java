package by.finalproject.itacademy.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class ApiBaseException extends RuntimeException {
    public ApiBaseException(String message) {
        super(message);
    }
}

// 400
@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException extends ApiBaseException {
    public BadRequestException(String message) {
        super(message);
    }
}

// 401
@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UnauthorizedException extends ApiBaseException {
    public UnauthorizedException(String message) {
        super(message);
    }
}

// 403
@ResponseStatus(HttpStatus.FORBIDDEN)
class ForbiddenException extends ApiBaseException {
    public ForbiddenException(String message) {
        super(message);
    }
}

