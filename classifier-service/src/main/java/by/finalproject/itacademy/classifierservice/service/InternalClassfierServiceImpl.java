package by.finalproject.itacademy.classifierservice.service;

import by.finalproject.itacademy.classifierservice.repository.CurrencyRepository;
import by.finalproject.itacademy.classifierservice.repository.OperationCategoryRepository;
import by.finalproject.itacademy.classifierservice.service.api.IInternalClassfierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InternalClassfierServiceImpl implements IInternalClassfierService {
    private final CurrencyRepository currencyRepository;
    private final OperationCategoryRepository operationCategoryRepository;

    @Override
    public boolean getCurrencyByUuid(UUID uuid) {
        return currencyRepository.existsById(uuid);
    }

    @Override
    public boolean getOperationCategoryByUuid(UUID uuid) {
        return operationCategoryRepository.existsById(uuid);
    }
}
