package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.film.FilmAlreadyExistExeption;
import ru.yandex.practicum.filmorate.exception.film.InvalidDescriptionExeption;
import ru.yandex.practicum.filmorate.exception.film.InvalidDurationExeption;
import ru.yandex.practicum.filmorate.exception.film.InvalidReleaseDateException;
import ru.yandex.practicum.filmorate.exception.user.InvalidNameExeption;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final InMemoryFilmStorage filmStorage;

@Autowired
    public FilmController(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @GetMapping("")
    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    @PostMapping("")
    public Film createFilm(@RequestBody Film film) throws ParseException {
        return filmStorage.createFilm(film);
    }

    @PutMapping("")
    public Film updateFilm(@RequestBody Film film) throws ParseException {
        return filmStorage.updateFilm(film);
    }


}
