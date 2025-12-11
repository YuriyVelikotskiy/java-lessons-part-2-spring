package application.model.mapper;

import application.model.dto.NotificationMessage;
import application.model.dto.UserResponse;
import application.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
    public static UserResponse mapToResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getAge(), user.getCreated_at());
    }
    public static NotificationMessage mapToNotificationMassage(User user, String type){
        return new NotificationMessage(user.getEmail(), type);
    }
}
