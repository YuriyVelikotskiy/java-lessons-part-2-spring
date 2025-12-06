package application.service;

import application.model.AvailableMessage;
import application.model.EmailMessage;
import application.model.NotificationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final String CREATE = "create";
    private static final String DELETE = "delete";
    private final MailService service;

    public void sendNotification(NotificationMessage message) {
        try {
            service.send(createMessage(message));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка mailService" + e);
        }
    }

    private EmailMessage createMessage(NotificationMessage message) {
        if (message.type().equals(CREATE)) {
            return new EmailMessage(message.email(), AvailableMessage.USER_CREATED.getSubject(), AvailableMessage.USER_CREATED.getBody());
        } else if (message.type().equals(DELETE)) {
            return new EmailMessage(message.email(), AvailableMessage.USER_DELETED.getSubject(), AvailableMessage.USER_DELETED.getBody());
        } else {
            throw new RuntimeException("Неверный тип");
        }
    }
}
