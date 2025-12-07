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
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@Tag(name = "Users", description = "Доступные операции")
public class UserController {
    private final UserService userService;
    private final ModelAssembler assembler;

    @GetMapping("/users")
    @Operation(summary = "Получение всех пользователей", description = "Возвращает список всех записей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список получен",
                    content = @Content(schema = @Schema(implementation = CollectionModel.class))),
            @ApiResponse(responseCode = "404", description = "Список не получен")
    })
    public ResponseEntity<CollectionModel<EntityModel<UserResponse>>> getAll() {
        try {
            CollectionModel<EntityModel<UserResponse>> returnedList = assembler.toCollectionModel(userService.getAll());
            return ResponseEntity.ok(returnedList);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Получение пользователя по id", description = "Возвращает запись с определенным id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь по id получен",
                    content = @Content(schema = @Schema(implementation = EntityModel.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь по id не найдены")
    })
    public ResponseEntity<EntityModel<UserResponse>> getUserById(@PathVariable Integer userId) {
        try {
            EntityModel<UserResponse> returnedUser = assembler.toModel(userService.findById(userId));
            return ResponseEntity.ok(returnedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user")
    @Operation(summary = "Создание нового пользователя", description = "Возвращает созданную запись")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь создан",
                    content = @Content(schema = @Schema(implementation = EntityModel.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка создания")
    })
    public ResponseEntity<EntityModel<UserResponse>> saveUser(@RequestBody UserRequest user) {
        try {
            EntityModel<UserResponse> returnedUser = assembler.toModel(userService.saveUser(user));
            URI uri = linkTo(methodOn(UserController.class).getUserById(returnedUser.getContent().id())).toUri();
            return ResponseEntity.created(uri).body(returnedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "Удаляет пользователя", description = "Возвращает сообщение об удалении")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найдены")
    })
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        try {
            userService.delete(userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/user")
    @Operation(summary = "Обновляет данные пользователя", description = "Возвращает обновленную запись")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен",
                    content = @Content(schema = @Schema(implementation = EntityModel.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найдены")
    })
    public ResponseEntity<EntityModel<UserResponse>> updateUser(@RequestBody UserRequest user) {
        try {
            EntityModel<UserResponse> returnedUser = assembler.toModel(userService.update(user));
            return ResponseEntity.ok(returnedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
