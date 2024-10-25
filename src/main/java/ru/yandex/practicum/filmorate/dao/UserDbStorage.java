package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.util.List;

public interface UserDbStorage {
    public List<User> findAllUsers();

    public User createUser(User user) throws ParseException;

    public User updateUser(User user) throws ParseException;

    public void deleteUser(String email) throws ParseException;

    public User findUserByEmail(String email);
}
