package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.util.List;

public interface UserStorage {
    public List<User> findAllUsers();

    public User createUser(User film) throws ParseException;

    public User updateUser(User film) throws ParseException;
}