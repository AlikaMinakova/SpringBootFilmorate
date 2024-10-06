package ru.yandex.practicum.filmorate.exception.film;

public class FilmAlreadyExistExeption extends RuntimeException{

    public FilmAlreadyExistExeption(final String message) {
        super(message);
    }
}