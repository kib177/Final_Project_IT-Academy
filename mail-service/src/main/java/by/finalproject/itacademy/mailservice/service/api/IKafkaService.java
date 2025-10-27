package by.finalproject.itacademy.mailservice.service.api;

import by.finalproject.itacademy.common.kafka.kafkaDTO.UserRegisteredEvent;
import by.finalproject.itacademy.common.kafka.kafkaDTO.UserVerifiedEvent;
import org.springframework.kafka.annotation.KafkaListener;

public interface IKafkaService {

    @KafkaListener(topics = "user-registered-topic")
    void handleUserRegistered(UserRegisteredEvent event);

    @KafkaListener(topics = "user-verified-topic")
    void handleUserVerified(UserVerifiedEvent event);
}
