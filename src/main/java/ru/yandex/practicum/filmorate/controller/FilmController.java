package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.film.FilmAlreadyExistExeption;
import ru.yandex.practicum.filmorate.exception.film.InvalidDurationExeption;
import ru.yandex.practicum.filmorate.exception.film.InvalidReleaseDateException;
import ru.yandex.practicum.filmorate.exception.user.InvalidNameExeption;
import ru.yandex.practicum.filmorate.model.Film;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private HashMap<String, Film> films = new HashMap<>();
    private static int id = 0;

    public void checkExeption(Film film) throws ParseException {
        if (film.getName().equals("")) {
            throw new InvalidNameExeption("the name should not be empty");
        }
        if (film.getDescription().length() > 200) {
            throw new InvalidNameExeption("the description should be no more than 200 characters long");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = simpleDateFormat.parse("1895-12-28");
        if (film.getReleaseDate().before(date)) {
            throw new InvalidReleaseDateException("the date should not be earlier than 28.12.1895");
        }
        if (film.getDuration() <= 0) {
            throw new InvalidDurationExeption("the name should not be <= 0");
        }
    }

    @GetMapping("")
    public List<Film> findAllFilms() {
        return (List<Film>) films.values();
    }

    @PostMapping("")
    public Film createFilm(@RequestBody Film film) throws ParseException {
        checkExeption(film);

        if (films.containsKey(film.getName())) {
            throw new FilmAlreadyExistExeption("the film already exists");
        }

        film.setId(++id);
        films.put(film.getName(), film);
        return film;
    }

    @PutMapping("")
    public Film updateFilm(@RequestBody Film film) throws ParseException {
        checkExeption(film);
        if (films.containsKey(film.getName())) {
            Film film1 = films.get(film.getName());
            film1.setDuration(film.getDuration());
            film1.setDescription(film.getDescription());
            film1.setReleaseDate(film.getReleaseDate());
            return films.get(film.getName());
        } else {
            return createFilm(film);
        }
    }


}
