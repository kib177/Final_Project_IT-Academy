package by.finalproject.itacademy.classifierservice.controller;

import by.finalproject.itacademy.classifierservice.model.dto.CurrencyDTO;
import by.finalproject.itacademy.classifierservice.model.dto.OperationCategoryDTO;
import by.finalproject.itacademy.classifierservice.model.dto.PageOfOperationCategory;
import by.finalproject.itacademy.classifierservice.service.api.IClassifierService;
import by.finalproject.itacademy.common.model.dto.PageDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/v1/classifier")
@AllArgsConstructor
public class ClassifierController {

    private IClassifierService classifierService;

    @GetMapping("/currency")
    public ResponseEntity<PageDTO<Object>> getPageOfCurrency(Pageable pageable) {
        return ResponseEntity.ok().body(classifierService.getPageOfCurrency(pageable));
    }

    @PostMapping("/currency")
    public ResponseEntity<String> addNewCurrency(@Valid @RequestBody CurrencyDTO currency) {
        classifierService.addNewCurrency(currency);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/operation/category")
    public ResponseEntity<PageDTO<Object>> getPageOfCategory(Pageable pageable) {
        return ResponseEntity.ok().body(classifierService.getPageOfOperationCategory(pageable));
    }

    @PostMapping("/operation/category")
    public ResponseEntity<String> addCategoryOperation(@RequestBody OperationCategoryDTO category) {
        classifierService.addNewOperationCategory(category);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
