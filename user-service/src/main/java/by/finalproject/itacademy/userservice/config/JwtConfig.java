package by.finalproject.itacademy.userservice.config;

import by.finalproject.itacademy.common.JwtAuthenticationFilter;
import by.finalproject.itacademy.common.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
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
        return  new JwtAuthenticationFilter(jwtTokenUtil());
    }
}

