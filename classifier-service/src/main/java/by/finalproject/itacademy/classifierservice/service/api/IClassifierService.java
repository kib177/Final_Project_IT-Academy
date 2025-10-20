package by.finalproject.itacademy.classifierservice.service.api;

import by.finalproject.itacademy.classifierservice.model.dto.*;
import org.springframework.data.domain.Pageable;


public interface IClassifierService {
    void addNewCurrency(CurrencyRequest currency);
    void addNewOperationCategory(OperationCategoryResponse operationCategory);
    PageOfOperationCategory getPageOfOperationCategory(Pageable pageable);
    PageOfCurrency getPageOfCurrency(Pageable pageable);
}
