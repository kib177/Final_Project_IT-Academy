package by.finalproject.itacademy.auditservice.feign;

import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        value = "user-service",
        url = "http://localhost:8080")

public interface UserServiceClient {

    @GetMapping("/api/v1/users/{uuid}")
    UserDTO getUser(@PathVariable UUID uuid);
}

