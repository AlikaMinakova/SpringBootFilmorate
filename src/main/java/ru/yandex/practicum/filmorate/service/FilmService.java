package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.exception.film.*;
import ru.yandex.practicum.filmorate.exception.user.InvalidNameExeption;
import ru.yandex.practicum.filmorate.model.Film;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class FilmService {

    private final FilmDbStorage filmDbStorage;

    @Autowired
    public FilmService(FilmDbStorage filmDbStorage) {
        //добавить проверки
        this.filmDbStorage = filmDbStorage;
    }

    public void checkExeption(Film film) throws ParseException {
        if (film.getName().equals("")) {
            log.debug("Ошибка добавления фильма. Пустое имя");
            throw new InvalidNameExeption("the name should not be empty");
        }
        if (film.getDescription().length() > 200) {
            log.debug("Ошибка добавления фильма. Описание больше 200 символов");
            throw new InvalidDescriptionExeption("the description should be no more than 200 characters long");
        }
        LocalDate date = LocalDate.parse("1895-12-28");
        if (date.isAfter(film.getReleaseDate())) {
            log.info("Ошибка создания фильма. Дата релиза должна быть не ранее 1895-12-28");
            throw new InvalidReleaseDateException("дата релиза должна быть не ранее 1895-12-28");
        }

        if (film.getDuration() <= 0) {
            log.debug("Ошибка добавления фильма. Длительность не должна быть <= 0");
            throw new InvalidDurationExeption("the duration should not be <= 0");
        }
    }

    public Film getFilmName(String name) {
        Film film = filmDbStorage.findByNameFilm(name);
        if (film != null) {
            return film;
        } else {
            throw new FilmNotFoundException("Фильма нет в бд");
        }

    }

    public void deleteFilmByName(String name) {
        filmDbStorage.deleteByNameFilm(name);
    }

    public List<Film> findAllFilms() {
        return filmDbStorage.findAllFilms();
    }

    public Film createFilm(Film film) throws ParseException {
        checkExeption(film);
        Film filmOptional = filmDbStorage.findByNameFilm(film.getName());
        if (filmOptional != null) {
            log.info("Ошибка создания фильма. Уже создан");
            throw new FilmAlreadyExistExeption("фильм уже создан");
        }
        return filmDbStorage.createFilm(film);
    }

    public Film updateFilm(Film film) throws ParseException {
        checkExeption(film);
        Film filmOptional = filmDbStorage.findByNameFilm(film.getName());
        if (filmOptional == null) {
            log.info("Ошибка обновления фильма. Нет в бд");
            throw new FilmNotFoundException("фильма нет в бд");
        }
        return filmDbStorage.updateFilm(film);
    }
}
