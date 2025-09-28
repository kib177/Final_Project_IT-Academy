package by.finalproject.itacademy.accountservice.feign;

import by.finalproject.itacademy.accountservice.model.dto.AuditEventRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "audit-service", url = "http://localhost:8081")
public interface AuditServiceClient {

    @PostMapping("/api/v1/audit/log")
    ResponseEntity<Void> logEvent(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody AuditEventRequest request);
}


