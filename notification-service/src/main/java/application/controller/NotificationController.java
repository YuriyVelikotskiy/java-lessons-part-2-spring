package application.controller;

import application.model.EmailMessage;
import application.model.NotificationMessage;
import application.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private NotificationService notificationService;

    @PostMapping("/notification/send")
    public ResponseEntity<EmailMessage> notification (@RequestBody NotificationMessage message){
        try {
            notificationService.sendNotification(message);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
