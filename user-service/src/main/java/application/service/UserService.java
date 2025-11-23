package application.service;

import application.model.dto.UserRequest;
import application.model.entity.User;
import application.model.dto.UserResponse;

import lombok.RequiredArgsConstructor;
import application.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse saveUser(UserRequest user) {
        Integer id = user.getId();
        if (id != null){
            throw new RuntimeException("id должен отсутствовать");
        }
        User userForSave = new User(user.getName(), user.getEmail(), user.getAge());
        User savedUser = userRepository.save(userForSave);
        return mapToResponse(savedUser);
    }

    public UserResponse findById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
        return mapToResponse(user);
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserService::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse update(UserRequest user){
        Integer id = user.getId();
        if (id == null){
            throw new RuntimeException("id не задан");
        }
        User userFromDB = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
        userFromDB.setName(user.getName());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setAge(user.getAge());
        User savedUser = userRepository.save(userFromDB);
        return mapToResponse(savedUser);
    }

    public void delete(Integer id){
        userRepository.deleteById(id);
    }

    private static UserResponse mapToResponse(User user){
       return new UserResponse(user.getName(), user.getEmail(), user.getAge(), user.getCreated_at());
    }
}
