package by.finalproject.itacademy.auditservice.feign;

/*import by.finalproject.itacademy.auditservice.model.dto.UserDTO;
import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import by.finalproject.itacademy.userservice.model.enums.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceClient {

    private final RestTemplate restTemplate;

    @Value("${user.service.url:http://user-service:8080}")
    private String userServiceUrl;

    @Value("${user.service.endpoint:/api/v1/users/{uuid}}")
    private String userEndpoint;

    public UserDTO getUserById(UUID userUuid, String authToken) {
        String url = UriComponentsBuilder.fromHttpUrl(userServiceUrl)
                .path(userEndpoint)
                .buildAndExpand(userUuid.toString())
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<UserEntity> response = restTemplate.getForEntity(url, UserEntity.class);

        return createFallbackUser(response.getBody() != null ? response.getBody() : null);
    }

    private UserDTO createFallbackUser(UserEntity userEntity) {
        UserDTO user = new UserDTO();
        user.setUuid(userEntity.getUuid());
        user.setFio(userEntity.getFio());
        user.setMail(userEntity.getMail());
        user.setRole(userEntity.getRole().toString());
        return user;
    }
}*/
