package application.controller;

import application.model.EmailMessage;
import org.springframework.http.ResponseEntity;

public class ControllerHandler {
    public static ResponseEntity<EmailMessage> trySend(Runnable runnable) {
        try {
            runnable.run();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
