package application.kafka.consumer;

import application.model.NotificationMessage;
import application.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActionKafkaConsumer {

    private final NotificationService service;

    @KafkaListener(topics = "${spring.kafka.topic.actions}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEvent(@Payload NotificationMessage message) {
        try {
            service.sendNotification(message);
        } catch (RuntimeException e){
            log.error("Ошибка - {}", e.getMessage());
        }
    }
}
