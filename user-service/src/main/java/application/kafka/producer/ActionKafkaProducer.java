package application.kafka.producer;

import application.model.dto.NotificationMessage;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionKafkaProducer {

    @Value("${spring.kafka.topic.actions}")
    private String topic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @CircuitBreaker(name = "kafkaBreaker", fallbackMethod = "fallbackSend")
    public void sendActionToKafka(NotificationMessage massage){
        kafkaTemplate.send(topic, massage);
    }

    public void fallbackSend(String topic, String message, Throwable t) {
        System.out.println("Не удалось отправить сообщение в Kafka.");
    }
}
