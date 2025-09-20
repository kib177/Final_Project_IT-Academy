package by.finalproject.itacademy.classifierservice.service;

import by.finalproject.itacademy.classifierservice.model.dto.CurrencyDTO;
import by.finalproject.itacademy.classifierservice.model.dto.OperationCategoryDTO;
import by.finalproject.itacademy.classifierservice.model.entity.CurrencyEntity;
import by.finalproject.itacademy.classifierservice.model.entity.OperationCategoryEntity;
import by.finalproject.itacademy.classifierservice.repository.CurrencyRepository;
import by.finalproject.itacademy.classifierservice.repository.OperationCategoryRepository;
import by.finalproject.itacademy.classifierservice.service.api.IClassifierService;
import by.finalproject.itacademy.classifierservice.service.mapper.CurrencyMapper;
import by.finalproject.itacademy.classifierservice.service.mapper.OperationCategoryMapper;
import by.finalproject.itacademy.common.model.dto.PageDTO;
import by.finalproject.itacademy.common.model.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.Instant.now;

@Service
@RequiredArgsConstructor
public class ClassifierService implements IClassifierService {
    private final CurrencyRepository currencyRepository;
    private final OperationCategoryRepository operationCategoryRepository;
    private final CurrencyMapper currencyMapper;
    private final OperationCategoryMapper operationCategoryMapper;

    @Override
    public void addNewCurrency(CurrencyDTO currency) {

        CurrencyEntity currencyEntity = currencyMapper.toEntity(currency);
        if (currencyEntity.getBaseEntity() != null) {
            currencyEntity.getBaseEntity().setDtCreate(now());
            currencyEntity.getBaseEntity().setDtUpdate(now());
        }
        currencyRepository.save(currencyEntity);
    }

    @Override
    public void addNewOperationCategory(OperationCategoryDTO operationCategory) {

        OperationCategoryEntity operationCategoryEntity = operationCategoryMapper.toEntity(operationCategory);
        if (operationCategoryEntity.getBaseEntity() != null) {
            operationCategoryEntity.getBaseEntity().setDtCreate(now());
            operationCategoryEntity.getBaseEntity().setDtUpdate(now());
        }
        operationCategoryRepository.save(operationCategoryEntity);
    }


    @Override
    public PageDTO<Object> getPageOfCurrency(Pageable pageable) {
        Page<CurrencyEntity> currencyPage = currencyRepository.findAll(pageable);
        List<Object> content = new ArrayList<>();
        for (CurrencyEntity currencyEntity : currencyPage.getContent()) {
            content.add(
                    CurrencyDTO.builder()
                            .uuid(currencyEntity.getBaseEntity().getUuid())
                            .dtCreate(currencyEntity.getBaseEntity().getDtCreate())
                            .dtUpdate(currencyEntity.getBaseEntity().getDtUpdate())
                            .title(currencyEntity.getTitle())
                            .description(currencyEntity.getDescription())
                            .build());
        }
        return PageDTO.builder()
                .number(currencyPage.getNumber())
                .size(currencyPage.getSize())
                .totalPages(currencyPage.getTotalPages())
                .totalElements(currencyPage.getTotalElements())
                .first(currencyPage.isFirst())
                .numberOfElements(currencyPage.getNumberOfElements())
                .last(currencyPage.isLast())
                .content(content)
                .build();
    }

    @Override
    public PageDTO<Object> getPageOfOperationCategory(Pageable pageable) {
        Page<OperationCategoryEntity> currencyPage = operationCategoryRepository.findAll(pageable);
        List<Object> content = new ArrayList<>();
        for (OperationCategoryEntity operationCategoryEntity : currencyPage.getContent()) {
            content.add(
                    operationCategoryMapper.toDTO(operationCategoryEntity)
            );
        }
        return PageDTO.builder()
                .number(currencyPage.getNumber())
                .size(currencyPage.getSize())
                .totalPages(currencyPage.getTotalPages())
                .totalElements(currencyPage.getTotalElements())
                .first(currencyPage.isFirst())
                .numberOfElements(currencyPage.getNumberOfElements())
                .last(currencyPage.isLast())
                .content(content)
                .build();
    }
}
