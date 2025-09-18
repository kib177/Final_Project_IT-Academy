package by.finalproject.itacademy.classifierservice.service;

import by.finalproject.itacademy.classifierservice.model.dto.CurrencyDTO;
import by.finalproject.itacademy.classifierservice.model.dto.OperationCategoryDTO;
import by.finalproject.itacademy.classifierservice.model.dto.PageOfCurrency;
import by.finalproject.itacademy.classifierservice.model.dto.PageOfOperationCategory;
import by.finalproject.itacademy.classifierservice.model.entity.CurrencyEntity;
import by.finalproject.itacademy.classifierservice.model.entity.OperationCategoryEntity;
import by.finalproject.itacademy.classifierservice.repository.CurrencyRepository;
import by.finalproject.itacademy.classifierservice.repository.OperationCategoryRepository;
import by.finalproject.itacademy.common.model.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassifierService implements IClassifierService {
    private final CurrencyRepository currencyRepository;
    private final OperationCategoryRepository operationCategoryRepository;

    @Override
    public void addNewCurrency(CurrencyDTO currency) {

        CurrencyEntity currencyEntity = CurrencyEntity.builder()
                .baseEntity(BaseEntity.builder()
                        .dtCreate(Instant.now())
                        .dtUpdate(Instant.now()).build())
                .description(currency.getDescription())
                .title(currency.getTitle())
                .build();
        currencyRepository.save(currencyEntity);
    }

    @Override
    public void addNewOperationCategory(OperationCategoryDTO operationCategory) {
        if (operationCategory.getUuid() == null) {
            operationCategory.setUuid(UUID.randomUUID());
        }
        if (operationCategory.getDtCreate() == 0) {
            operationCategory.setDtCreate(Instant.now().toEpochMilli());
        }
        if (operationCategory.getDtUpdate() == 0) {
            operationCategory.setDtUpdate(Instant.now().toEpochMilli());
        }

        OperationCategoryEntity operationCategoryEntity = OperationCategoryEntity.builder()
                .baseEntity(BaseEntity.builder()
                        .dtCreate(Instant.now())
                        .dtUpdate(Instant.now())
                        .build())
                .title(operationCategory.getTitle())
                .build();
        operationCategoryRepository.save(operationCategoryEntity);
    }

    @Override
    public PageOfOperationCategory getPageOfOperationCategory(Pageable pageable) {
        return classifierStorage.getAllOperationCategories(pageable);
    }

    @Override
    public PageOfCurrency getPageOfCurrency(Pageable pageable) {
        return classifierStorage.getAllCurrencies(pageable);
    }
}
}
