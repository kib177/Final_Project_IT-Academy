package by.finalproject.itacademy.mailservice.service.api;

import java.util.Map;

public interface ITemplateService {
    String loadTemplate(String templateName, Map<String, String> variables);
}
