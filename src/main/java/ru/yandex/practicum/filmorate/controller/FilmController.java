package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.film.FilmAlreadyExistExeption;
import ru.yandex.practicum.filmorate.exception.film.InvalidDescriptionExeption;
import ru.yandex.practicum.filmorate.exception.film.InvalidDurationExeption;
import ru.yandex.practicum.filmorate.exception.film.InvalidReleaseDateException;
import ru.yandex.practicum.filmorate.exception.user.InvalidNameExeption;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

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

    private HashMap<String, Film> films = new HashMap<>();
    private static int id = 0;

    public void checkExeption(Film film) throws ParseException {
        if (film.getName().equals("")) {
            log.debug("Ошибка добавления фильма. Пустое имя");
            throw new InvalidNameExeption("the name should not be empty");
        }
        if (film.getDescription().length() > 200) {
            log.debug("Ошибка добавления фильма. Описание больше 200 символов");
            throw new InvalidDescriptionExeption("the description should be no more than 200 characters long");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("1895-12-28");
        if (film.getReleaseDate().before(date)) {
            log.debug("Ошибка добавления фильма. Дата релиза раньше 28.12.1895");
            throw new InvalidReleaseDateException("the date should not be earlier than 28.12.1895");
        }
        if (film.getDuration() <= 0) {
            log.debug("Ошибка добавления фильма. Длительность не должна быть <= 0");
            throw new InvalidDurationExeption("the duration should not be <= 0");
        }
    }

    @GetMapping("")
    public List<Film> findAllFilms() {
        log.debug("Получен список фильмов: {}", new ArrayList<Film>(films.values()));
        return new ArrayList<Film>(films.values());
    }

    @PostMapping("")
    public Film createFilm(@RequestBody Film film) throws ParseException {
        checkExeption(film);

        if (films.containsKey(film.getName())) {
            log.debug("Ошибка добавления фильма. Уже существует: {}", film);
            throw new FilmAlreadyExistExeption("the film already exists");
        }

        film.setId(++id);
        films.put(film.getName(), film);
        log.debug("Добавлен новый фильм: {}", film);
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
            log.debug("Обновлён фильм: {}", film);
            return films.get(film.getName());
        } else {
            return createFilm(film);
        }
    }


}
