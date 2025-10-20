package by.finalproject.itacademy.classifierservice.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenUtil {
    //private final SecretKey secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${jwt.expiration:3600000}")
    private Long expiration;

    public String generateToken(UUID userId, String email, String fio, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId.toString())
                .claim("role", role)
                .claim("fio", fio)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256,"mySuperSecretKeyThatIsVeryLongAndHardToGuess12345!")
                .compact();
    }

    public JwtUser extractUser(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey("mySuperSecretKeyThatIsVeryLongAndHardToGuess12345!")
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new JwtUser(
                UUID.fromString(claims.get("userId", String.class)),
                claims.getSubject(),
                claims.get("fio", String.class),
                claims.get("role", String.class)

        );
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey("mySuperSecretKeyThatIsVeryLongAndHardToGuess12345!")
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Date getExpirationDate(String token) {
        return Jwts.parserBuilder()
                .setSigningKey("mySuperSecretKeyThatIsVeryLongAndHardToGuess12345!")
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }
}