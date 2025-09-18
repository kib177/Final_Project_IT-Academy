package by.finalproject.itacademy.classifierservice.service;

import by.finalproject.itacademy.classifierservice.model.dto.CurrencyDTO;
import by.finalproject.itacademy.classifierservice.model.dto.OperationCategoryDTO;
import by.finalproject.itacademy.classifierservice.model.dto.PageOfCurrency;
import by.finalproject.itacademy.classifierservice.model.dto.PageOfOperationCategory;

import java.awt.print.Pageable;

public interface IClassifierService {
    void addNewCurrency(CurrencyDTO currency);
    void addNewOperationCategory(OperationCategoryDTO operationCategory);
    PageOfOperationCategory getPageOfOperationCategory(Pageable pageable);
    PageOfCurrency getPageOfCurrency(Pageable pageable);
}
