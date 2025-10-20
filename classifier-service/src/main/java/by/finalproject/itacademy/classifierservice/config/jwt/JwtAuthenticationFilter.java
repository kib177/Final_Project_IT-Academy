package by.finalproject.itacademy.classifierservice.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/api/v1/audit/log".equals(path)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = extractToken(request);

            if (token != null && jwtTokenUtil.validateToken(token)) {
                JwtUser jwtUser = jwtTokenUtil.extractUser(token);

                if (jwtTokenUtil.isTokenExpired(token)) {
                    logger.warn("JWT token expired for user: {}", jwtUser.email());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                    return;
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                jwtUser,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + jwtUser.role()))
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Authenticated user: {} with role: {}", jwtUser.email(), jwtUser.role());
            }
        } catch (Exception e) {
            logger.error("JWT authentication failed: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
