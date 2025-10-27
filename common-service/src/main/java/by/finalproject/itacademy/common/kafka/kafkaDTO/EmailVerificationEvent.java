package by.finalproject.itacademy.common.kafka.kafkaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class EmailVerificationEvent {
        private UUID eventId;
        private LocalDateTime eventTime;
        private String email;
        private String verificationCode;
        private UUID userId;
    }
