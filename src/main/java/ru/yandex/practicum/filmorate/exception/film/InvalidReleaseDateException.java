package ru.yandex.practicum.filmorate.exception.film;

public class InvalidReleaseDateException extends RuntimeException{

    public InvalidReleaseDateException(final String message) {
        super(message);
    }
}