package ru.yandex.practicum.filmorate.exception.film;

public class InvalidDurationExeption extends RuntimeException{

    public InvalidDurationExeption(final String message) {
        super(message);
    }
}