package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("")
    public List<Film> findAllFilms() {
        return filmService.findAllFilms();
    }

    @GetMapping("/{name}")
    public Film getFilmId(@PathVariable String name) {
        return filmService.getFilmName(name);
    }

//    @PutMapping("/{id}/like/{userId}")
//    public void addLikeFilm(@RequestBody String id, @RequestBody String userId) throws ParseException {
//        filmService.addLike(Integer.parseInt(id), Integer.parseInt(userId));
//    }
//
    @DeleteMapping("/{name}")
    public void deleteLikeFilm(@PathVariable String name) throws ParseException {
        filmService.deleteFilmByName(name);
    }
//
//    @GetMapping("/popular")
//    public List<Film> getTopFilms(@RequestParam(value = "count", defaultValue = "10") String count) {
//        return filmService.findTop10Likes(Integer.parseInt(count));
//    }

    @PostMapping("")
    public Film createFilm(@RequestBody Film film) throws ParseException {
        return filmService.createFilm(film);
    }

    @PutMapping("")
    public Film updateFilm(@RequestBody Film film) throws ParseException {
        return filmService.updateFilm(film);
    }


}
