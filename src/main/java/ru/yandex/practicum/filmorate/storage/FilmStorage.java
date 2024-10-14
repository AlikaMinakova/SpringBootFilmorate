package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.text.ParseException;
import java.util.List;

public interface FilmStorage {

    public List<Film> findAllFilms();

    public Film createFilm(Film film) throws ParseException;

    public Film updateFilm(Film film) throws ParseException;
}
