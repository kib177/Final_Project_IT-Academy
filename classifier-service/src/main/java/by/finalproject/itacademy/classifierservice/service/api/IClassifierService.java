package by.finalproject.itacademy.classifierservice.service.api;

import by.finalproject.itacademy.classifierservice.model.dto.CurrencyDTO;
import by.finalproject.itacademy.classifierservice.model.dto.OperationCategoryDTO;
import by.finalproject.itacademy.common.model.dto.PageDTO;

import java.awt.print.Pageable;

public interface IClassifierService {
    void addNewCurrency(CurrencyDTO currency);
    void addNewOperationCategory(OperationCategoryDTO operationCategory);
    PageDTO<Object> getPageOfOperationCategory(Pageable pageable);
    PageDTO<Object> getPageOfCurrency(Pageable pageable);
}
