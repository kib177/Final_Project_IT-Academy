package by.finalproject.itacademy.classifierservice.controller;

import by.finalproject.itacademy.classifierservice.service.api.IInternalClassfierService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/classifier")
@AllArgsConstructor
public class InternalClassifierController {
    private IInternalClassfierService internalClassfierService;

    @GetMapping("/currency/{uuid}")
    public ResponseEntity<Boolean> getSpecificCurrency(@PathVariable UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(internalClassfierService.getCurrencyByUuid(uuid));
    }
    @GetMapping("/category/{uuid}")
    public ResponseEntity<Boolean> getSpecificCategory(@PathVariable UUID uuid ) {
        return ResponseEntity.status(HttpStatus.OK).body(internalClassfierService.getOperationCategoryByUuid(uuid));
    }

}
