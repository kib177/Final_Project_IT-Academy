package by.finalproject.itacademy.mailservice.service.api;

public interface IVerificationCodeService {
    String generateCode(String uMail);

    boolean validateCode(String mail, String code);

    void deleteCode(String mail);
   /* boolean validateCode(String mail, String code);
    void deleteCode(String mail);*/
}
