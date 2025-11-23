package application.repository;

import application.model.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class UserRepositoryTest {
    static User firstUser;
    static User secondUser;
    static User userForUpdate;
    static User userForDelete;
    @Autowired
    private UserRepository repository;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @BeforeAll
    static void init() {
        firstUser = new User("Test", "test@test.ru", 20);
        firstUser.setCreated_at(LocalDate.of(2000, 1, 1));

        secondUser = new User("TEST", "TEST@test.ru", 40);
        userForUpdate = new User("UpName", "up@test.ru", 10);
        userForDelete = new User("DeName", "de@test.ru", 10);
    }

    @Test
    @DisplayName("Поиск записи по существующему id после создания записи")
    void shouldBeFoundAfterAdd() {
        repository.save(firstUser);

        Optional<User> returnedUser = repository.findById(firstUser.getId());

        assertTrue(returnedUser.isPresent());
        assertEquals(firstUser, returnedUser.get());
    }

    @Test
    @DisplayName("Выгрузка всех записей после создания записей")
    void shouldBeGetAllAfterAdd() {
        repository.save(firstUser);
        repository.save(secondUser);

        List<User> returnedUser = repository.findAll();

        assertFalse(returnedUser.isEmpty());
        assertTrue(returnedUser.size() >= 2);
    }

    @Test
    @DisplayName("Обновление записи после создания записи после создания записи")
    void shouldBeUpdated() {
        String nameForUpdate = "UpdatedName";
        String emailForUpdate = "updated@test.ru";
        Integer ageForUpdate = 22;

        User returnedUser = repository.save(userForUpdate);

        returnedUser.setName(nameForUpdate);
        returnedUser.setAge(ageForUpdate);
        returnedUser.setEmail(emailForUpdate);
        User updatedUser = repository.save(returnedUser);

        assertEquals(updatedUser.getName(), nameForUpdate);
        assertEquals(updatedUser.getAge(), ageForUpdate);
        assertEquals(updatedUser.getEmail(), emailForUpdate);
    }

    @Test
    @DisplayName("Удаление записи после создания записи после создания записи")
    void shouldBeDeleted() {
        User returnedUser = repository.save(userForDelete);

        Integer id = returnedUser.getId();
        repository.deleteById(id);
        Optional<User> deletedUser = repository.findById(id);

        assertTrue(deletedUser.isEmpty());
    }
}
