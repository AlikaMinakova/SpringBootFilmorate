package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendDbStorage;
import ru.yandex.practicum.filmorate.exception.user.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@Slf4j
public class FriendService {

    private final FriendDbStorage friendDbStorage;
    private final UserService userService;

    @Autowired
    public FriendService(FriendDbStorage friendDbStorage, UserService userService) {
        this.friendDbStorage = friendDbStorage;
        this.userService = userService;
    }

    public List<User> getFriendsByEmail(String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new NotFoundUserException("пользователя нет в бд");
        }
        return friendDbStorage.getFriendsByEmail(email);
    }

    public void addFriend(String user_email, String friend_email) {
        User user = userService.getUserByEmail(user_email);
        if (user == null) {
            throw new NotFoundUserException("пользователя нет в бд");
        }
        User friend = userService.getUserByEmail(friend_email);
        if (friend == null) {
            throw new NotFoundUserException("друга нет в бд");
        }
        friendDbStorage.addFriend(user_email, friend_email);
    }

    public void deleteFriend(String user_email, String friend_email) {
        User user = userService.getUserByEmail(user_email);
        if (user == null) {
            throw new NotFoundUserException("пользователя нет в бд");
        }
        User friend = userService.getUserByEmail(friend_email);
        if (friend == null) {
            throw new NotFoundUserException("друга нет в бд");
        }
        friendDbStorage.deleteFriend(user_email, friend_email);
    }

}
