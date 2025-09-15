package by.finalproject.itacademy.accountservice.feign;

import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.common.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "audit-service", url = "${audit-service.url}", configuration = FeignConfig.class)
public interface AuditServiceClient {
    @PostMapping("/api/v1/audit")
    void createAuditRecord(
            @RequestParam("userUuid") UUID userUuid,
            @RequestParam("text") String text,
            @RequestParam("type") EssenceTypeEnum type,
            @RequestParam("id") String id);
}