package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final FilmService filmService;

@Autowired
    public FilmController(FilmService filmStorage) {
        this.filmService = filmStorage;
    }

    @GetMapping("")
    public List<Film> findAllFilms() {
        return filmService.findAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmId(@PathVariable String id) {
        return filmService.getFilmId(Integer.parseInt(id));
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeFilm(@RequestBody String id, @RequestBody String userId) throws ParseException {
        filmService.addLike(Integer.parseInt(id), Integer.parseInt(userId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikeFilm(@RequestBody String id, @RequestBody String userId) throws ParseException {
        filmService.deleteLike(Integer.parseInt(id), Integer.parseInt(userId));
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(value = "count", defaultValue = "10") String count) {
        return filmService.findTop10Likes(Integer.parseInt(count));
    }

    @PostMapping("")
    public Film createFilm(@RequestBody Film film) throws ParseException {
        return filmService.createFilm(film);
    }

    @PutMapping("")
    public Film updateFilm(@RequestBody Film film) throws ParseException {
        return filmService.updateFilm(film);
    }


}
