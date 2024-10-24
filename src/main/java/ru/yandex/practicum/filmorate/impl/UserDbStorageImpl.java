package ru.yandex.practicum.filmorate.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.user.NotFoundUserException;
import ru.yandex.practicum.filmorate.exception.user.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class UserDbStorageImpl implements UserDbStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAllUsers() {
        String sql = "select * from users";
        log.info("получен список фильмов");
        return jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                String email = rs.getString("email");
                String login = rs.getString("login");
                String name = rs.getString("name");
                LocalDate birthday = LocalDate.parse(rs.getString("birthday"));
                return new User(email, login, name, birthday);
            }
        });
    }

    @Override
    public User createUser(User user) throws ParseException {
        User userOptional = findUserByEmail(user.getEmail());
        if (userOptional != null) {
            log.info("Ошибка создания пользователя. Уже создан");
            throw new UserAlreadyExistException("пользователь уже создан");
        }
        String sql = "insert into users (email, login, name, birthday) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        log.info("Фильм {} создан", user.getEmail());
        return user;
    }

    @Override
    public User updateUser(User user) throws ParseException {
        User userOptional = findUserByEmail(user.getEmail());
        if (userOptional == null) {
            log.info("Ошибка обновления пользователя. Нет в бд");
            throw new NotFoundUserException("пользователя нет в бд");
        }
        String sql = "update users set login = ?, name = ?, birthday = ? where email = ?";
        jdbcTemplate.update(sql, user.getLogin(), user.getName(), user.getBirthday(), user.getEmail());
        log.info("Обновлён фильм {}", user.getEmail());
        return user;
    }

    @Override
    public void deleteUser(String email) throws ParseException {
        String sql = "delete from users where email = ?";
        log.info("пользователь {} удалён", email);
        jdbcTemplate.update(sql, email);
    }

    @Override
    public User findUserByEmail(String email) throws ParseException {
        SqlRowSet userRow = jdbcTemplate.queryForRowSet("select * from users where email = ?", email);
        if (userRow.next()) {
            User user = new User(Objects.requireNonNull(userRow.getString("email")),
                    Objects.requireNonNull(userRow.getString("login")),
                    Objects.requireNonNull(userRow.getString("name")),
                    LocalDate.parse(Objects.requireNonNull(userRow.getString("birthday"))));
            log.info("получен пользователь с email {}", email);
            return user;
        }
        return null;
    }
}
