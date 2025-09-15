package by.finalproject.itacademy.auditservice.client;

import by.finalproject.itacademy.common.config.FeignConfig;
import by.finalproject.itacademy.userservice.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service", url = "${user-service.url}", configuration = FeignConfig.class)
public interface UserServiceClient {
    @GetMapping("/api/v1/users/{uuid}")
    User getUser(@PathVariable UUID uuid);
}
