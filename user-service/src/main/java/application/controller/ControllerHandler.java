package application.controller;

import application.model.dto.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

public class ControllerHandler {
    public static ResponseEntity<UserResponse> tryUpdate(Callable<UserResponse> callable) {
        try {
            UserResponse savedUser = callable.call();
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
