package by.finalproject.itacademy.reportservice.config;

import by.finalproject.itacademy.common.jwt.JwtAuthenticationFilter;
import by.finalproject.itacademy.common.jwt.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

}
