package by.finalproject.itacademy.userservice.service;

import by.finalproject.itacademy.userservice.service.api.IVerificationCodeService;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

@Service
public class VerificationCodeServiceImpl implements IVerificationCodeService {
    private final Map<String, String> codes = new ConcurrentHashMap<>();
    private final Random random = new Random();

    @Override
    public String generateCode(String mail) {
        String code = String.format("%06d", random.nextInt(999999));
        codes.put(mail, code);
        return code;
    }

    @Override
    public boolean validateCode(String mail, String code) {
        return codes.getOrDefault(mail, "").equals(code);
    }

    @Override
    public void deleteCode(String mail) {
        codes.remove(mail);
    }
}