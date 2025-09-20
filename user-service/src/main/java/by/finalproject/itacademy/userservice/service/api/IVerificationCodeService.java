package by.finalproject.itacademy.userservice.service.api;

import by.finalproject.itacademy.userservice.model.dto.UserCreate;

public interface IVerificationCodeService {
    void generateCode(String uMail);
   /* boolean validateCode(String mail, String code);
    void deleteCode(String mail);*/
}
