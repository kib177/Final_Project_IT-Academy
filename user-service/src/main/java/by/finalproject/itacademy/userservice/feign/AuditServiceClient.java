package by.finalproject.itacademy.userservice.feign;


import by.finalproject.itacademy.userservice.model.dto.UserLogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;


@FeignClient(name = "audit-service", url = "http://localhost:8081")
public interface AuditServiceClient {

    @PostMapping("/api/v1/audit/log")
    void logAction(@RequestBody UserLogDTO userLogDTO);
}

