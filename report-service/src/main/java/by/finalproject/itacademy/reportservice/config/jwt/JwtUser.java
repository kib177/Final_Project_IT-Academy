package by.finalproject.itacademy.reportservice.config.jwt;

import java.util.UUID;

public record JwtUser(UUID userId, String email, String fio, String role) {
    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }

    public boolean isUser() {
        return "USER".equals(role);
    }

    public boolean isManager() {
        return "MANAGER".equals(role);
    }
}