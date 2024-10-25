package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.dao.LikeDbStorage;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.like.LikeAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
public class LikeDbStorageImpl implements LikeDbStorage {

    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userDbStorage;
    private final FilmDbStorage filmDbStorage;

    @Autowired
    public LikeDbStorageImpl(JdbcTemplate jdbcTemplate, UserDbStorage userDbStorage, FilmDbStorage filmDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDbStorage = userDbStorage;
        this.filmDbStorage = filmDbStorage;
    }

    @Override
    public List<User> getUsersByFilmName(String name) {
        String sql = "select * from likes where film_name = ?";
        log.info("получен список пользователей, оценвших фильм {}", name);
        List<Like> likes = jdbcTemplate.query(sql, this::makeLike, name);
        return likes.stream().map(x -> userDbStorage.findUserByEmail(x.getUser())).toList();
    }

    @Override
    public List<Film> getFilmsByUserEmail(String email) {
        String sql = "select * from likes where user_email = ?";
        log.info("получен список фильмов, понравившихся пользователю {}", email);
        List<Like> likes = jdbcTemplate.query(sql, this::makeLike, email);
        return likes.stream().map(x -> filmDbStorage.findByNameFilm(x.getFilm())).toList();
    }

    @Override
    public void deleteUserByFilm(String name, String email) {
        String sql = "delete from likes where film_name = ? and user_email = ?";
        log.info("пользователь {} убрал свой лайк фильму {}", email, name);
        jdbcTemplate.update(sql, name, email);
    }

    @Override
    public List<Film> getTopFilms(int count) {
        String sql = "select * from likes where film_name in " +
                "(select film_name from likes group by film_name order by count(user_email) desc limit ?)";
        log.info("получен список топовых фильмов");
        List<Like> likes = jdbcTemplate.query(sql, this::makeLike, count);
        return likes.stream().map(x -> filmDbStorage.findByNameFilm(x.getFilm())).distinct().toList();
    }

    @Override
    public void addLike(String film_name, String user_email) {
        String sql = "select * from likes where film_name = ? and user_email = ?";
        List<Like> likes = jdbcTemplate.query(sql, this::makeLike, film_name, user_email);
        if (likes.isEmpty()){
            log.info("Пользователь {} оценил фильм {}", user_email, film_name);
            String sqlAdd = "insert into likes (film_name, user_email) values (?, ?)";
            jdbcTemplate.update(sqlAdd, film_name, user_email);
        }
        else {
            throw new LikeAlreadyExistsException("пользователь уже оценил этот фильм");
        }
    }

    public Like makeLike(ResultSet rs, int rowNum) throws SQLException {
        String user_email = rs.getString("user_email");
        String film_name = rs.getString("film_name");
        return new Like(film_name, user_email);
    }


}
