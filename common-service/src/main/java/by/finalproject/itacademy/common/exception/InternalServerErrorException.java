package by.finalproject.itacademy.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 500
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends ApiBaseException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
