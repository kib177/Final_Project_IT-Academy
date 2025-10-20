package by.finalproject.itacademy.classifierservice.controller;

import by.finalproject.itacademy.classifierservice.model.dto.*;
import by.finalproject.itacademy.classifierservice.service.api.IClassifierService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/classifier")
@AllArgsConstructor
public class ClassifierController {

    private IClassifierService classifierService;

    @GetMapping("/currency")
    public ResponseEntity<PageOfCurrency> getPageOfCurrency(Pageable pageable) {
        return ResponseEntity.ok().body(classifierService.getPageOfCurrency(pageable));
    }

    @PostMapping("/currency")
    public ResponseEntity<?> addNewCurrency(@Valid @RequestBody CurrencyRequest currency) {
        classifierService.addNewCurrency(currency);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/operation/category")
    public ResponseEntity<PageOfOperationCategory> getPageOfCategory(Pageable pageable) {
        return ResponseEntity.ok().body(classifierService.getPageOfOperationCategory(pageable));
    }

    @PostMapping("/operation/category")
    public ResponseEntity<?> addCategoryOperation(@Valid @RequestBody OperationCategoryResponse category) {
        classifierService.addNewOperationCategory(category);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
