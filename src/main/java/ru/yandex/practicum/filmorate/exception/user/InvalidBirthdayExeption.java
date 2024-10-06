package ru.yandex.practicum.filmorate.exception.user;

public class InvalidBirthdayExeption extends RuntimeException{

    public InvalidBirthdayExeption(final String message) {
        super(message);
    }
}