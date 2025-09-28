package by.finalproject.itacademy.classifierservice.service;

import by.finalproject.itacademy.classifierservice.model.dto.*;
import by.finalproject.itacademy.classifierservice.model.entity.CurrencyEntity;
import by.finalproject.itacademy.classifierservice.model.entity.OperationCategoryEntity;
import by.finalproject.itacademy.classifierservice.repository.CurrencyRepository;
import by.finalproject.itacademy.classifierservice.repository.OperationCategoryRepository;
import by.finalproject.itacademy.classifierservice.service.api.IClassifierService;
import by.finalproject.itacademy.classifierservice.service.mapper.CurrencyMapper;
import by.finalproject.itacademy.classifierservice.service.mapper.OperationCategoryMapper;
import by.finalproject.itacademy.common.model.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassifierServiceImpl implements IClassifierService {
    private final CurrencyRepository currencyRepository;
    private final OperationCategoryRepository operationCategoryRepository;
    private final CurrencyMapper currencyMapper;
    private final OperationCategoryMapper operationCategoryMapper;

    @Override
    public void addNewCurrency(CurrencyRequest currency) {
       currencyRepository.save(CurrencyEntity.builder()
                .dtCreate(LocalDateTime.now())
                .dtUpdate(LocalDateTime.now())
                .title(currency.getTitle())
                .description(currency.getDescription())
                .build());
    }

    @Override
    public void addNewOperationCategory(OperationCategoryResponse operationCategory) {
        OperationCategoryEntity operationCategoryEntity = operationCategoryMapper.toEntity(operationCategory);
            operationCategoryEntity.setDtCreate(LocalDateTime.now());
            operationCategoryEntity.setDtUpdate(operationCategoryEntity.getDtCreate());
        operationCategoryRepository.save(operationCategoryEntity);
    }

    @Override
    public PageDTO<Object> getPageOfCurrency(Pageable pageable) {
        Page<CurrencyEntity> currencyPage = currencyRepository.findAll(pageable);
        List<Object> currencyList = new ArrayList<>();
        for(CurrencyEntity currencyEntity : currencyPage.getContent()) {
            currencyList.add(currencyMapper.toDTO(currencyEntity));
        }
        return PageOfCurrency.builder()
                .number(currencyPage.getNumber())
                .size(currencyPage.getSize())
                .totalPages(currencyPage.getTotalPages())
                .totalElements(currencyPage.getTotalElements())
                .first(currencyPage.isFirst())
                .numberOfElements(currencyPage.getNumberOfElements())
                .last(currencyPage.isLast())
                .content(currencyList)
                .build();
    }

    @Override
    public PageOfOperationCategory getPageOfOperationCategory(Pageable pageable) {
        Page<OperationCategoryEntity> operationCategoryPage = operationCategoryRepository.findAll(pageable);
        return operationCategoryMapper.toPageOfOperationCategory(operationCategoryPage, operationCategoryMapper);
    }
}
