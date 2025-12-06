package application.service;

import application.kafka.producer.ActionKafkaProducer;
import application.model.dto.UserRequest;
import application.model.entity.User;
import application.model.dto.UserResponse;

import application.model.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import application.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static application.model.mapper.EntityMapper.mapToNotificationMassage;
import static application.model.mapper.EntityMapper.mapToResponse;


@Service
@RequiredArgsConstructor
public class UserService {
    private static final String CREATE = "create";
    private static final String DELETE = "delete";
    private final UserRepository userRepository;
    private final ActionKafkaProducer actionKafkaProducer;

    @Transactional
    public UserResponse saveUser(UserRequest user) {
        Integer id = user.id();
        if (id != null) {
            throw new RuntimeException("id должен отсутствовать");
        }
        User userForSave = new User(user.name(), user.email(), user.age());
        User savedUser = userRepository.save(userForSave);
        actionKafkaProducer.sendActionToKafka(mapToNotificationMassage(userForSave, CREATE));
        return mapToResponse(savedUser);
    }

    @Transactional
    public UserResponse findById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
        return mapToResponse(user);
    }

    @Transactional
    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(EntityMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse update(UserRequest user) {
        Integer id = user.id();
        if (id == null) {
            throw new RuntimeException("id не задан");
        }
        User userFromDB = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
        userFromDB.setName(user.name());
        userFromDB.setEmail(user.email());
        userFromDB.setAge(user.age());
        User savedUser = userRepository.save(userFromDB);
        return mapToResponse(savedUser);
    }

    @Transactional
    public void delete(Integer id) {
        User userForDelete = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
        userRepository.deleteById(id);
        actionKafkaProducer.sendActionToKafka(mapToNotificationMassage(userForDelete, DELETE));
    }
}
