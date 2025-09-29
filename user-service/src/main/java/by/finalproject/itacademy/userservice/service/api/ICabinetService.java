package by.finalproject.itacademy.userservice.service.api;

import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserLogin;
import by.finalproject.itacademy.userservice.model.dto.UserRegistration;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.UUID;

public interface ICabinetService {
    void registration(UserRegistration userRegistration) throws BadRequestException;
    String login(UserLogin userLogin) throws BadRequestException;
    void verifyUser(String mail, String code) throws BadRequestException;
    User getAboutSelf() throws BadRequestException;
}
