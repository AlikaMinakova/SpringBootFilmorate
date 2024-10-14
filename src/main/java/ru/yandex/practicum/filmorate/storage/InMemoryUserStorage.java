package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.user.InvalidBirthdayExeption;
import ru.yandex.practicum.filmorate.exception.user.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.user.InvalidLoginExeption;
import ru.yandex.practicum.filmorate.exception.user.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private HashMap<String, User> users = new HashMap<>();
    private static int id = 0;

    public void checkExeption(User user) throws ParseException {
        if (user.getEmail().equals("") || !user.getEmail().contains("@")) {
            log.debug("Ошибка добавления пользователя. Некорректно введён email");
            throw new InvalidEmailException("InvalidEmailException");
        }
        if (user.getLogin().equals("") || user.getLogin().contains(" ")) {
            log.debug("Ошибка добавления пользователя. Некорректно введён логин");
            throw new InvalidLoginExeption("InvalidLogin");
        }
        if (user.getName().equals("")) {
            user.setName(user.getLogin());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(String.valueOf(LocalDate.now()));
        if (user.getBirthday().after(date)) {
            log.debug("Ошибка добавления пользователя. Некорректно введена дата рождения");
            throw new InvalidBirthdayExeption("InvalidBirthdayExeption");
        }
    }

    @Override
    public List<User> findAllUsers() {
        log.debug("Получен список пользователей: {}", new ArrayList<User>(users.values()));
        return new ArrayList<User>(users.values());
    }

    @Override
    public User createUser(User user) throws ParseException {
        checkExeption(user);

        if (users.containsKey(user.getEmail())) {
            log.debug("Ошибка добавления пользователя. Пользователь уже существует");
            throw new UserAlreadyExistException("user already exists");
        }

        user.setId(++id);
        users.put(user.getEmail(), user);
        log.debug("Пользователь добавлен {}", user);
        return user;
    }

    @Override
    public User updateUser(User user) throws ParseException {
        checkExeption(user);
        if (users.containsKey(user.getEmail())) {
            User user1 = users.get(user.getEmail());
            user1.setBirthday(user.getBirthday());
            user1.setName(user.getName());
            user1.setLogin(user.getLogin());
            log.debug("Обновление пользователя {}", user);
            return users.get(user.getEmail());
        } else {
            return createUser(user);
        }
    }
}
