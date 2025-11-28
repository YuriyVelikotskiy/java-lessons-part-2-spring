package application.model.dto;

import java.time.LocalDate;

public record UserRequest(Integer id, String name, String email, Integer age) {
}
