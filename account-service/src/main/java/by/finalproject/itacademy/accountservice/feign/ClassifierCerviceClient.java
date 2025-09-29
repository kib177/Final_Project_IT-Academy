package by.finalproject.itacademy.accountservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "classifier-service", url = "http://localhost:8083")
public interface ClassifierCerviceClient {

    @GetMapping("/api/classifier/currency/{uuid}")
    boolean getSpecificCurrency(@PathVariable UUID uuid);

    @GetMapping("/api/classifier/category/{uuid}")
    boolean getSpecificCategory(@PathVariable UUID uuid);
}
