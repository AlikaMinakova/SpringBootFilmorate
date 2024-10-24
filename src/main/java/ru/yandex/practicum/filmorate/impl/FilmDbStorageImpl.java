package ru.yandex.practicum.filmorate.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.exception.film.FilmAlreadyExistExeption;
import ru.yandex.practicum.filmorate.exception.film.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class FilmDbStorageImpl implements FilmDbStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Film> findAllFilms() {
        String sql = "select * from films";
        log.info("получен список фильмов");
        return jdbcTemplate.query(sql, new RowMapper<Film>() {
            @Override
            public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
                String name = rs.getString("name");
                String description = rs.getString("description");
                LocalDate releaseDate = LocalDate.parse(rs.getString("releasedate"));
                Double duration = Double.valueOf(rs.getString("duration"));
                return new Film(name, description, releaseDate, duration);
            }
        });
    }

    @Override
    public Film findByNameFilm(String name) {
        SqlRowSet filmRow = jdbcTemplate.queryForRowSet("select * from films where name = ?", name);
        if (filmRow.next()) {
            Film film = new Film(Objects.requireNonNull(filmRow.getString("name")),
                    Objects.requireNonNull(filmRow.getString("description")),
                    LocalDate.parse(Objects.requireNonNull(filmRow.getString("releasedate"))),
                    Double.valueOf(Objects.requireNonNull(filmRow.getString("duration"))));
            log.info("получен фильм {}", name);
            return film;
        }
        return null;
    }

    @Override
    public void deleteByNameFilm(String name) {
        String sql = "delete from films where name = ?";
        log.info("фильм {} удалён", name);
        jdbcTemplate.update(sql, name);
    }

    @Override
    public Film createFilm(Film film) throws ParseException {
        Film filmOptional = findByNameFilm(film.getName());
        if (filmOptional != null) {
            log.info("Ошибка создания фильма. Уже создан");
            throw new FilmAlreadyExistExeption("фильм уже создан");
        }
        String sql = "insert into films (name, description, releasedate, duration) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        log.info("Фильм {} создан", film.getName());
        return film;
    }

    @Override
    public Film updateFilm(Film film) throws ParseException {
        Film filmOptional = findByNameFilm(film.getName());
        if (filmOptional == null) {
            log.info("Ошибка обновления фильма. Нет в бд");
            throw new FilmNotFoundException("фильма нет в бд");
        }
        String sql = "update films set description = ?, releasedate = ?, duration = ? where name = ?";
        jdbcTemplate.update(sql, film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getName());
        log.info("Обновлён фильм {}", film.getName());
        return film;
    }
}
