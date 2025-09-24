package by.finalproject.itacademy.accountschedulerservice.feign;

import by.finalproject.itacademy.accountservice.model.entity.AccountEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        value = "user-service",
        path = "/api/v1/user",
        url = "http://localhost:8082")
public interface AccountServiceClient {

    @GetMapping("/{uuid}")
    public AccountEntity getAccount(@PathVariable UUID uuid);
}