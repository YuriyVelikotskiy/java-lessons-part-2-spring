package application.controller;

import application.model.dto.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

public class ControllerHandler {
    public static ResponseEntity<UserResponse> tryRequest(Callable<UserResponse> callable) {
        try {
            UserResponse User = callable.call();
            return ResponseEntity.ok(User);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    public static ResponseEntity<String> tryDelete(Runnable runnable) {
        try {
            runnable.run();
            return ResponseEntity.ok("Todo deleted successfully!.");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
