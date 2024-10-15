package ru.yandex.practicum.filmorate.exception.user;

public class NotFoundUserException extends RuntimeException {

    public NotFoundUserException(final String message) {
        super(message);
    }
}