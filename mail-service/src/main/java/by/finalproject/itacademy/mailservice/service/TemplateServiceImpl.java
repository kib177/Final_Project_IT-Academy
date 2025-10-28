package by.finalproject.itacademy.mailservice.service;

import by.finalproject.itacademy.mailservice.service.api.ITemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
public class TemplateServiceImpl implements ITemplateService {

    @Override
    public String loadTemplate(String templateName, Map<String, String> variables) {
        try {
            String template = loadTemplateFromResources(templateName);
            return replaceVariables(template, variables);
        } catch (IOException e) {
            log.error("Failed to load template: {}", templateName, e);
            throw new RuntimeException("Failed to load email template: " + templateName, e);
        }
    }

    private String loadTemplateFromResources(String templateName) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/" + templateName);
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

    private String replaceVariables(String template, Map<String, String> variables) {
        String result = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return result;
    }
}
