package by.finalproject.itacademy.userservice.service.api;

public interface IVerificationCodeService {
    String generateCode(String mail);
    boolean validateCode(String mail, String code);
    void deleteCode(String mail);
}
