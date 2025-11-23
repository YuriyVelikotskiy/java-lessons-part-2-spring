package application.controller;

import application.model.dto.UserRequest;
import application.model.dto.UserResponse;
import application.service.UserService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    private static UserResponse firstUser;
    private static UserResponse secondUser;
    private static UserRequest user;
    @Autowired
    private MockMvc mockMvc;
    private static ObjectMapper objectMapper;
    @MockitoBean
    private UserService service;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
        user = new UserRequest("Test", "test@test.ru", 20);
        firstUser = new UserResponse("Test", "test@test.ru", 20, LocalDate.of(2000, 1, 1));
        secondUser = new UserResponse("TEST", "TEST@test.ru", 40, LocalDate.of(2020, 1, 1));
    }

    @Test
    @DisplayName("Метод должен вернуть запись по id")
    void shouldReturnUserById() throws Exception {
        Integer userId = 1;
        when(service.findById(userId)).thenReturn(firstUser);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"));
        verify(service).findById(userId);
    }

    @Test
    @DisplayName("Метод должен вернуть List")
    void shouldReturnListOfUsers() throws Exception {
        List<UserResponse> list = List.of(firstUser, secondUser);
        when(service.getAll()).thenReturn(list);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].name").value(firstUser.getName()))
                .andExpect(jsonPath("$.[1].name").value(secondUser.getName()));
        verify(service).getAll();
    }

    @Test
    @DisplayName("Метод должен вернуть новую запись")
    void shouldCreateUser() throws Exception {
        when(service.saveUser(any(UserRequest.class))).thenReturn(firstUser);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(firstUser.getName()))
                .andExpect(jsonPath("$.age").value(firstUser.getAge()))
                .andExpect(jsonPath("$.email").value(firstUser.getEmail()));
        verify(service).saveUser(any(UserRequest.class));
    }

    @Test
    @DisplayName("Метод должен обновить запись")
    void updateUser_Success() throws Exception {
        when(service.update(any(UserRequest.class))).thenReturn(firstUser);

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(firstUser.getName()))
                .andExpect(jsonPath("$.age").value(firstUser.getAge()))
                .andExpect(jsonPath("$.email").value(firstUser.getEmail()));
        verify(service).update(any(UserRequest.class));
    }

    @Test
    @DisplayName("Метод должен удалить запись")
    void shouldDeleteUser() throws Exception {

        doNothing().when(service).delete(1);

        mockMvc.perform(delete("/users/1")).andExpect(status().isOk());

        verify(service).delete(1);
    }
}
