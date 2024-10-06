package ru.yandex.practicum.filmorate.exception.user;

public class UserAlreadyExistException extends RuntimeException{

    public UserAlreadyExistException(final String message) {
        super(message);
    }
}