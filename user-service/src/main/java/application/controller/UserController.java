package application.controller;

import application.model.dto.UserRequest;
import application.model.dto.UserResponse;
import application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{userId}")
    public UserResponse getUserById(@PathVariable Integer userId) {
        return userService.findById(userId);
    }

    @PostMapping("/users")
    public UserResponse saveUser(@RequestBody UserRequest user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        userService.delete(userId);
        return ResponseEntity.ok("Todo deleted successfully!.");
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest user){
        try {
            UserResponse updatedUser = userService.update(user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
