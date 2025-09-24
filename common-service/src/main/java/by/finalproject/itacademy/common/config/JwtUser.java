package by.finalproject.itacademy.common.config;

import java.util.UUID;

public record JwtUser(UUID userId, String email, String role) {
    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }
}