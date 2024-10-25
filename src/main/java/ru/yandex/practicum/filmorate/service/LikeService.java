package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.LikeDbStorage;
import ru.yandex.practicum.filmorate.exception.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.user.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
public class LikeService {
    private final LikeDbStorage likeDbStorage;
    private final UserService userService;
    private final FilmService filmService;

    @Autowired
    public LikeService(LikeDbStorage likeDbStorage, UserService userService, FilmService filmService) {
        this.likeDbStorage = likeDbStorage;
        this.userService = userService;
        this.filmService = filmService;
    }

    public List<User> getUsersByFilmName(String name) {
        Film film = filmService.getFilmName(name);
        if (film == null) {
            throw new FilmNotFoundException("фильма нет в бд");
        }
        return likeDbStorage.getUsersByFilmName(name);
    }

    public List<Film> getFilmsByUserEmail(String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new NotFoundUserException("пользователя нет в бд");
        }
        return likeDbStorage.getFilmsByUserEmail(email);
    }

    public void deleteUserByFilm(String name, String email) {
        Film film = filmService.getFilmName(name);
        if (film == null) {
            throw new FilmNotFoundException("фильма нет в бд");
        }
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new NotFoundUserException("пользователя нет в бд");
        }
        likeDbStorage.deleteUserByFilm(name, email);
    }

    public List<Film> getTopFilms(int count) {
        return likeDbStorage.getTopFilms(count);
    }

    public void addLike(String film_name, String user_email) {
        Film film = filmService.getFilmName(film_name);
        if (film == null) {
            throw new FilmNotFoundException("фильма нет в бд");
        }
        User user = userService.getUserByEmail(user_email);
        if (user == null) {
            throw new NotFoundUserException("пользователя нет в бд");
        }
        likeDbStorage.addLike(film_name, user_email);
    }
}
