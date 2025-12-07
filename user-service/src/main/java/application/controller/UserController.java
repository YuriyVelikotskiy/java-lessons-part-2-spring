package application.controller;

import application.model.dto.UserRequest;
import application.model.dto.UserResponse;
import application.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static application.controller.ControllerHandler.tryDelete;
import static application.controller.ControllerHandler.tryRequest;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users", description = "Доступные операции")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    @Operation(summary = "Получение всех пользователей", description = "Возвращает список всех записей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список получен",
                    content = @Content(schema = @Schema(implementation = CollectionModel.class))),
            @ApiResponse(responseCode = "404",description = "Список не получен")
    })
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Получение пользователя по id", description = "Возвращает запись с определенным id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь по id получен",
                    content = @Content(schema = @Schema(implementation = EntityModel.class))),
            @ApiResponse(responseCode = "404",description = "Пользователь по id не найдены")
    })
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer userId) {
        return tryRequest(() -> userService.findById(userId));
    }

    @PostMapping("/user")
    @Operation(summary = "Создание нового пользователя", description = "Возвращает созданную запись")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь создан",
                    content = @Content(schema = @Schema(implementation = EntityModel.class))),
            @ApiResponse(responseCode = "400",description = "Ошибка создания")
    })
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest user) {
        return tryRequest(() -> userService.saveUser(user));
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "Удаляет пользователя", description = "Возвращает сообщение об удалении")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "404",description = "Пользователь не найдены")
    })
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        return tryDelete(()->userService.delete(userId));
    }

    @PutMapping("/users")
    @Operation(summary = "Обновляет данные пользователя", description = "Возвращает обновленную запись")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен",
                    content = @Content(schema = @Schema(implementation = EntityModel.class))),
            @ApiResponse(responseCode = "404",description = "Пользователь не найдены")
    })
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest user) {
        return tryRequest(() -> userService.update(user));
    }
}
