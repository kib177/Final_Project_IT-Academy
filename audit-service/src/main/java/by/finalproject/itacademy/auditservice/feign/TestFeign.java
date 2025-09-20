package by.finalproject.itacademy.auditservice.feign;


import by.finalproject.itacademy.auditservice.feign.api.auditApi;
import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.UUID;

@FeignClient(
        value = "user-service",
        path = "/api/v1/user",
        url = "http://localhost:8080")

public interface TestFeign extends auditApi {
    @Override
    @GetMapping("/{uuid}")
    public UserDTO getUser(@PathVariable UUID uuid);
}
