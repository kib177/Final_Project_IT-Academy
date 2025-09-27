package by.finalproject.itacademy.common.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private SecretKey secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(UUID userId, String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId.toString())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secret)
                .compact();
    }

    public JwtUser extractUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return new JwtUser(
                UUID.fromString(claims.get("userId", String.class)),
                claims.getSubject(),
                claims.get("role", String.class)
        );
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}