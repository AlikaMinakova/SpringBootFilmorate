package ru.yandex.practicum.filmorate.exception.user;

public class InvalidEmailException extends RuntimeException{

    public InvalidEmailException(final String message) {
        super(message);
    }
}