package application.controller;

import application.model.EmailMessage;
import application.model.NotificationMessage;
import application.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static application.controller.ControllerHandler.trySend;

@RestController
public class NotificationController {

    private NotificationService notificationService;

    @PostMapping("/notification/send")
    public ResponseEntity<EmailMessage> notification (@RequestBody NotificationMessage message){
        return trySend(()->notificationService.sendNotification(message));
    }
}
