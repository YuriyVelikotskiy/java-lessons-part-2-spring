package application.kafka.producer;

import application.model.dto.NotificationMessage;
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

    public void sendActionToKafka(NotificationMessage massage){
        kafkaTemplate.send(topic, massage);
    }
}
