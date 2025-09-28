package by.finalproject.itacademy.accountservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "classifier-service", url = "http://localhost:8083")
public interface ClassifierCerviceClient {

    @GetMapping("/api/classifier/currency/{uuid}")
    boolean getSpecificCurrency(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable UUID uuid);

    @GetMapping("/api/classifier/category/{uuid}")
    boolean getSpecificCategory(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable UUID uuid);
}
