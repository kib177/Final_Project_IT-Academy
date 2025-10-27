package by.finalproject.itacademy.common.kafka.kafkaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserVerifiedEvent {
        private UUID eventId;
        private LocalDateTime eventTime;
        private String email;
        private UUID userId;
        private String code;
    }
