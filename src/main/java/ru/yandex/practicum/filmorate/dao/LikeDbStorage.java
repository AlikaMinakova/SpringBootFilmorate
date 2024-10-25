package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface LikeDbStorage {

    public List<User> getUsersByFilmName(String name);

    public List<Film> getFilmsByUserEmail(String email);

    public void deleteUserByFilm(String name, String email);

    public List<Film> getTopFilms(int count);

    public void addLike(String film_name, String user_email);


}
