package application.service;

import application.model.dto.UserRequest;
import application.model.dto.UserResponse;
import application.model.entity.User;
import application.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class UserServiceTest {

    private static User user;
    private static User updatedUser;
    private static UserResponse userResponse;
    private static UserResponse userResponseForUpdate;
    private static UserRequest userRequestForSave;
    private static UserRequest userRequestForUpdate;
    @MockitoBean
    private UserRepository mockRepository;
    @Autowired
    private UserService service;

    @BeforeAll
    static void init() {
        Integer userId = 1;
        String name = "Test";
        String updatedName = "Updated";
        String email = "test@test.ru";
        Integer age = 20;
        LocalDate creatDate = LocalDate.of(2000, 1, 1);

        user = new User();
        user.setId(userId);
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        user.setCreated_at(creatDate);

        updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName(updatedName);
        updatedUser.setEmail(email);
        updatedUser.setAge(age);
        updatedUser.setCreated_at(creatDate);

        userResponse = new UserResponse(name, email, age, creatDate);
        userResponseForUpdate = new UserResponse(updatedName, email, age, creatDate);
        userRequestForSave = new UserRequest(null, name, email, age);
        userRequestForUpdate = new UserRequest(userId, name, email, age);
    }

    @Test
    @DisplayName("Поиск записи по id")
    void shouldFindUser() {
        when(mockRepository.findById(1)).thenReturn(Optional.of(user));

        UserResponse returnedUser = service.findById(1);

        verify(mockRepository, times(1)).findById(1);
        assertEquals(userResponse, returnedUser);
    }

    @Test
    @DisplayName("Выгрузка всех записей")
    void shouldReturnListOfUsers() {
        when(mockRepository.findAll()).thenReturn(new ArrayList<>());

        List<UserResponse> returnedList = service.getAll();

        verify(mockRepository, times(1)).findAll();
        assertTrue(returnedList.isEmpty());
    }

    @Test
    @DisplayName("Сохранение записи")
    void shouldBeSave() {
        when(mockRepository.save(any(User.class))).thenReturn(user);

        UserResponse savedUser = service.saveUser(userRequestForSave);

        verify(mockRepository, times(1)).save(any(User.class));
        assertEquals(savedUser, userResponse);
    }

    @Test
    @DisplayName("Обновление записи")
    void shouldBeUpdate() {
        when(mockRepository.findById(1)).thenReturn(Optional.of(user));
        when(mockRepository.save(any(User.class))).thenReturn(updatedUser);

        UserResponse updatedUserFromService = service.update(userRequestForUpdate);

        verify(mockRepository, times(1)).save(any(User.class));
        assertEquals(updatedUserFromService, userResponseForUpdate);
    }

    @Test
    @DisplayName("Удаление записи")
    void shouldBeDelete() {
        doNothing().when(mockRepository).deleteById(1);

        service.delete(1);

        verify(mockRepository).deleteById(1);
    }
}
