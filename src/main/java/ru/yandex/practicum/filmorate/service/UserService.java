package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.user.NotFoundUserException;
import ru.yandex.practicum.filmorate.exception.user.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User getUserId(Integer id) {
        List<Integer> usersId = userStorage.findAllUsers().stream().map(User::getId).toList();
        if (id <= 0 || !usersId.contains(id)){
            throw new NotFoundUserException("Пользователь с таким id не найден");
        }
        return userStorage.findAllUsers().stream().filter(x -> x.getId() == id).toList().get(0);
    }

    public List<User> findAllFriends(Integer userId) {
        User user = getUserId(userId);
        Set<Integer> friends = user.getFriends();
        log.debug("Получен список друзей пользователя с id: {}", userId);
        return userStorage.findAllUsers().stream().filter(x -> friends.contains(x.getId())).toList();
    }

    public void addFriend(Integer userFirstId, Integer userFriendId) {
        User user1 = getUserId(userFirstId);
        Set<Integer> friends1 = user1.getFriends();
        friends1.add(userFriendId);

        User user2 = getUserId(userFriendId);
        Set<Integer> friends2 = user2.getFriends();
        friends2.add(userFirstId);
        log.debug("Пользователи с id {} и {} стали друзьями", userFirstId, userFriendId);
    }

    public void deleteFriend(Integer userFirstId, Integer userFriendId) {
        User user1 = getUserId(userFirstId);
        Set<Integer> friends1 = user1.getFriends();
        friends1.remove(userFriendId);

        User user2 = getUserId(userFriendId);
        Set<Integer> friends2 = user2.getFriends();
        friends2.remove(userFirstId);
        log.debug("Пользователи с id {} и {} больше не друзья", userFirstId, userFriendId);
    }

    public List<User> getCommonFriends(Integer userFirstId, Integer otherId) {
        List<User> userFirstFriends = findAllFriends(userFirstId);
        List<User> otherFriends = findAllFriends(otherId);
        return userFirstFriends.stream()
                .filter(otherFriends::contains).toList();
    }

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User createUser(User user) throws ParseException {
        return userStorage.createUser(user);
    }

    public User updateUser(User user) throws ParseException {
        return userStorage.updateUser(user);
    }
}
