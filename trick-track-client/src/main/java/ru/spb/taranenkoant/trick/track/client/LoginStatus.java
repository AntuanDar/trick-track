package ru.spb.taranenkoant.trick.track.client;

/**
 * Created by Anton on 24.03.2023
 * description: enum - особый тип классов в java
 */
public enum LoginStatus {

    SUCCESS(""),
    INVALID_USER("Пользователь не найден"),
    INVALID_PASSWORD("Неверный пароль"),
    INVALID_HOST("Сервер не найден");

    private final String message;

    LoginStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
