package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.text.ParseException;
import java.util.*;

@Service
@Slf4j
public class FilmService {

    private final FilmDbStorage filmDbStorage;
    //private final UserService userService;

    @Autowired
    public FilmService(FilmDbStorage filmDbStorage) {
        //добавить проверки
        this.filmDbStorage = filmDbStorage;
    }

    public Film getFilmName(String name) {
        return filmDbStorage.findAllFilms().stream().filter(x -> x.getName().equals(name)).toList().get(0);
    }

//    public List<Film> findTop10Likes(Integer count) {
//        if (count <= 0) {
//            throw new IncorrectCountValueException("значение count должно быть >= 0");
//        }
//        log.debug("Получено топ-10 фильмов");
//        return filmStorage.findAllFilms()
//                .stream()
//                .sorted(Comparator.comparing(x -> x.getLikesFromUsers().size()))
//                .sorted(Collections.reverseOrder())
//                .limit(count)
//                .toList();
//        //переопределив в класе Film compareTo(), могли просто написать .sorted()
//        // и оно бы отсортировалось по переопределённым правилам
//    }

//    public void addLike(Integer userId, Integer filmId) {
//        Film film1 = getFilmId(filmId);
//        Set<Integer> friends = film1.getLikesFromUsers();
//        friends.add(userId);
//        log.debug("фильму {} добавил лайк пользователь {}", filmId, userId);
//    }
//
    public void deleteFilmByName(String name) {
        filmDbStorage.deleteByNameFilm(name);
    }

    public List<Film> findAllFilms() {
        return filmDbStorage.findAllFilms();
    }

    public Film createFilm(Film film) throws ParseException {
        return filmDbStorage.createFilm(film);
    }

    public Film updateFilm(Film film) throws ParseException {
        return filmDbStorage.updateFilm(film);
    }
}
