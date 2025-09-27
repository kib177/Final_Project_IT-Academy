package by.finalproject.itacademy.accountservice.service;

import by.finalproject.itacademy.accountservice.model.dto.OperationDTO;
import by.finalproject.itacademy.accountservice.model.dto.PageOfOperation;
import by.finalproject.itacademy.accountservice.model.entity.AccountEntity;
import by.finalproject.itacademy.accountservice.model.entity.OperationEntity;
import by.finalproject.itacademy.accountservice.repository.AccountRepository;
import by.finalproject.itacademy.accountservice.repository.OperationRepository;
import by.finalproject.itacademy.accountservice.service.api.IOperationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class OperationServiceImpl implements IOperationService {
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;
    private final IClassifierServiceClient  classifierServiceClient;

    @Override
    @Transactional
    public void save(UUID accountUuid, OperationDTO operation) {

        OperationEntity operationEntity = OperationEntity.builder()
                .dtCreate(LocalDateTime.now())
                .dtUpdate(LocalDateTime.now())
                .account(accountRepository.getReferenceById(accountUuid))
                .date(operation.getDate())
                .description(operation.getDescription())
                .category(categoryUuid)
                .value(operation.getValue())
                .currency(currencyUuid)
                .build();
        operationRepository.save(operationEntity);
    }

    @Override
    @Transactional
    public void update(UUID uuid, UUID OperationUuid, LocalDateTime dtUpdate, OperationDTO operation) {
        OperationEntity entity = operationRepository.getReferenceById(uuid);
        OperationCategory operationCategory = classifierServiceClient.getCategory(operation.getCategory());
        Currency currency = classifierServiceClient.getCurrency(operation.getCurrency());

        entity.setDtUpdate(dtUpdate);
        operationRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(UUID uuid, UUID OperationUuid, long dtUpdate) {
        AccountEntity entity  = accountRepository.getReferenceById(uuid);
        entity.setDtUpdate(dtUpdate);
        accountRepository.save(entity);
        operationRepository.deleteById(OperationUuid);
    }

    @Override
    public PageOfOperation getPage(UUID uuid, int page, int size) {
        Page<OperationEntity> operationPage = operationRepository.findByAccountUuid(uuid, PageRequest.of(page, size));
        List<OperationDTO> content = new ArrayList<>();
        for (OperationEntity operationEntity : operationPage.getContent()) {
            content.add(OperationDTO.builder()
                    .uuid(operationEntity.getUuid())
                    .dtCreate(operationEntity.getDtCreate())
                    .dtUpdate(operationEntity.getDtUpdate())
                    .date(operationEntity.getDate())
                    .description(operationEntity.getDescription())
                    .category(classifierServiceClient.getCategory(uuid).getTitle())
                    .value(operationEntity.getValue())
                    .currency(classifierServiceClient.getCurrency(operationEntity.getCurrency()).getTitle())
                    .build());
        }
        return PageOfOperation.builder()
                .number(operationPage.getNumber())
                .size(operationPage.getSize())
                .totalPages(operationPage.getTotalPages())
                .totalElements(operationPage.getTotalElements())
                .first(operationPage.isFirst())
                .numberOfElements(operationPage.getNumberOfElements())
                .last(operationPage.isLast())
                .content(content)
                .build();

    }
}
