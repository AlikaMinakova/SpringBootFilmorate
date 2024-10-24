package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.text.ParseException;
import java.util.List;

public interface FilmDbStorage {

    public List<Film> findAllFilms();

    public Film createFilm(Film film) throws ParseException;

    public Film updateFilm(Film film) throws ParseException;

    public Film findByNameFilm(String name);

    public void deleteByNameFilm(String name);
}