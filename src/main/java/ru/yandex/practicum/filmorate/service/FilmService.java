package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.film.IncorrectCountValueException;
import ru.yandex.practicum.filmorate.exception.user.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film getFilmId(Integer id) {
        List<Integer> filmsId = filmStorage.findAllFilms().stream().map(Film::getId).toList();
        if (id <= 0 || !filmsId.contains(id)) {
            throw new FilmNotFoundException("Фильм с таким id не найден");
        }
        return filmStorage.findAllFilms().stream().filter(x -> x.getId() == id).toList().get(0);
    }

    public List<Film> findTop10Likes(Integer count) {
        if (count <= 0) {
            throw new IncorrectCountValueException("значение count должно быть >= 0");
        }
        log.debug("Получено топ-10 фильмов");
        return filmStorage.findAllFilms()
                .stream()
                .sorted(Comparator.comparing(x -> x.getLikesFromUsers().size()))
                .sorted(Collections.reverseOrder())
                .limit(count)
                .toList();
        //переопределив в класе Film compareTo(), могли просто написать .sorted()
        // и оно бы отсортировалось по переопределённым правилам
    }

    public void addLike(Integer userId, Integer filmId) {
        Film film1 = getFilmId(filmId);
        Set<Integer> friends = film1.getLikesFromUsers();
        friends.add(userId);
        log.debug("фильму {} добавил лайк пользователь {}", filmId, userId);
    }

    public void deleteLike(Integer userId, Integer filmId) {
        Film film1 = getFilmId(filmId);
        Set<Integer> friends = film1.getLikesFromUsers();
        try {
            friends.remove(userId);
        } catch (Exception e) {
            throw new NotFoundUserException("пользователя с таким id не существует");
        }
        log.debug("пользователь {} удалил свой лайк фильму {}", userId, filmId);
    }

    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    public Film createFilm(Film film) throws ParseException {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) throws ParseException {
        return filmStorage.updateFilm(film);
    }
}
