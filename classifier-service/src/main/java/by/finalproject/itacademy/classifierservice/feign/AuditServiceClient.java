package by.finalproject.itacademy.classifierservice.feign;


import by.finalproject.itacademy.classifierservice.model.dto.AuditEventRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "audit-service", url = "http://localhost:8081")
public interface AuditServiceClient {

    @PostMapping("/api/v1/audit/log")
    ResponseEntity<Void> logEvent(@RequestBody AuditEventRequest request);
}


