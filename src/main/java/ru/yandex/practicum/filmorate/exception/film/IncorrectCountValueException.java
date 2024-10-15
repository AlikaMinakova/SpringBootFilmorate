package ru.yandex.practicum.filmorate.exception.film;

public class IncorrectCountValueException extends RuntimeException{

    public IncorrectCountValueException(final String message) {
        super(message);
    }
}
