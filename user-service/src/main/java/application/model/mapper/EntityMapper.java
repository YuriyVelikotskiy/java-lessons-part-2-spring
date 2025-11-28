package application.model.mapper;

import application.model.dto.UserResponse;
import application.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
    public static UserResponse mapToResponse(User user) {
        return new UserResponse(user.getName(), user.getEmail(), user.getAge(), user.getCreated_at());
    }
}
