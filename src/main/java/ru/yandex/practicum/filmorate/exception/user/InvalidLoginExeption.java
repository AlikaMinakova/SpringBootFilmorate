package ru.yandex.practicum.filmorate.exception.user;

public class InvalidLoginExeption extends RuntimeException{

    public InvalidLoginExeption(final String message) {
        super(message);
    }
}
