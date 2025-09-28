package by.finalproject.itacademy.classifierservice.service.api;

import by.finalproject.itacademy.classifierservice.model.dto.CurrencyResponse;
import by.finalproject.itacademy.classifierservice.model.dto.OperationCategoryResponse;

import java.util.UUID;

public interface IInternalClassfierService {
    boolean getCurrencyByUuid(UUID uuid);

    boolean getOperationCategoryByUuid(UUID uuid);
}
