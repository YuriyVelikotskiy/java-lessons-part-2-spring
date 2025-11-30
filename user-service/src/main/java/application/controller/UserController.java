package application.controller;

import application.model.dto.UserRequest;
import application.model.dto.UserResponse;
import application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static application.controller.ControllerHandler.tryDelete;
import static application.controller.ControllerHandler.tryRequest;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer userId) {
        return tryRequest(() -> userService.findById(userId));
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest user) {
        return tryRequest(() -> userService.saveUser(user));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        return tryDelete(()->userService.delete(userId));
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest user) {
        return tryRequest(() -> userService.update(user));
    }
}
