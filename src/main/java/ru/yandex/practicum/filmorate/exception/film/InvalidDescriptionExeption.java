package ru.yandex.practicum.filmorate.exception.film;

public class InvalidDescriptionExeption extends RuntimeException{

    public InvalidDescriptionExeption(final String message) {
        super(message);
    }
}
