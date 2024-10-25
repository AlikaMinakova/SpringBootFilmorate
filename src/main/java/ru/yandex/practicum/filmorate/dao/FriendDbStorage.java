package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendDbStorage {
    public List<User> getFriendsByEmail(String email);

    public void addFriend(String user_email, String friend_email);

    public void deleteFriend(String user_email, String friend_email);
}
