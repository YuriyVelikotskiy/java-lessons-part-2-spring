package application.model.dto;

import java.time.LocalDate;

public record UserResponse (String name, String email, Integer age, LocalDate created_at) {
}
