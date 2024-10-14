package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.text.ParseException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final InMemoryUserStorage userStorage;

    @Autowired
    public UserController(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @GetMapping("")
    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) throws ParseException {
        return userStorage.createUser(user);
    }

    @PutMapping("")
    public User updateUser(@RequestBody User user) throws ParseException {
        return userStorage.updateUser(user);
    }
}
