package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.film.InvalidDurationExeption;
import ru.yandex.practicum.filmorate.exception.film.InvalidReleaseDateException;
import ru.yandex.practicum.filmorate.exception.user.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private HashMap<String, User> users = new HashMap<>();
    private static int id = 0;

    public void checkExeption(User user) throws ParseException {
        if (user.getEmail().equals("") || !user.getEmail().contains("@")) {
            throw new InvalidEmailException("InvalidEmailException");
        }
        if (user.getLogin().equals("") || user.getLogin().contains(" ")) {
            throw new InvalidLoginExeption("InvalidLogin");
        }
        if (user.getName().equals("")) {
            user.setName(user.getLogin());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = simpleDateFormat.parse(String.valueOf(LocalDate.now()));
        if (user.getBirthday().after(date)){
            throw new InvalidBirthdayExeption("InvalidBirthdayExeption");
        }
    }

    @GetMapping("")
    public List<User> findAllUsers() {
        return (List<User>) users.values();
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) throws ParseException {
        checkExeption(user);

        if (users.containsKey(user.getEmail())) {
            throw new UserAlreadyExistException("user already exists");
        }

        user.setId(++id);
        users.put(user.getEmail(), user);
        return user;
    }

    @PutMapping("")
    public User updateUser(@RequestBody User user) throws ParseException {
        checkExeption(user);
        if (users.containsKey(user.getEmail())) {
            User user1 = users.get(user.getEmail());
            user1.setBirthday(user.getBirthday());
            user1.setName(user.getName());
            user1.setLogin(user.getLogin());
            return users.get(user.getEmail());
        } else {
            return createUser(user);
        }
    }
}
