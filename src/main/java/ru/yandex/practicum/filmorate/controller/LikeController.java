package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.LikeService;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{film_id}/{user_id}")
    public void addLikeFilm(@PathVariable String film_id, @PathVariable String user_id) {
        likeService.addLike(film_id, user_id);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(value = "count", defaultValue = "10") String count) {
        return likeService.getTopFilms(Integer.parseInt(count));
    }

    @GetMapping("/users/{film_id}")
    public List<User> getUsersByFilmName(@PathVariable String film_id) {
        return likeService.getUsersByFilmName(film_id);
    }

    @GetMapping("/films/{user_id}")
    public List<Film> getFilmsByUserEmail(@PathVariable String user_id) {
        return likeService.getFilmsByUserEmail(user_id);
    }

    @DeleteMapping("/{film_id}/{user_id}")
    public void deleteUserByFilm(@PathVariable String film_id, @PathVariable String user_id) {
        likeService.deleteUserByFilm(film_id, user_id);
    }
}
