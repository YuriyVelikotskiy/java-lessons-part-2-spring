package application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AvailableMessage {
    USER_CREATED("Создание аккаунта", "Здравствуйте! Ваш аккаунт был успешно создан."),
    USER_DELETED("Удаление аккаунта", "Здравствуйте! Ваш аккаунт был удалён");

    private final String subject;
    private final String body;
}

