package ru.yandex.practicum.filmorate.exception.user;

public class InvalidNameExeption extends RuntimeException {

    public InvalidNameExeption(final String message) {
        super(message);
    }
}
