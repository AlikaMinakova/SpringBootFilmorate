package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.exception.user.*;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserDbStorage userDbStorage;

    @Autowired
    public UserService(UserDbStorage userDbStorage) {
        this.userDbStorage = userDbStorage;
    }

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
        LocalDate date = LocalDate.now();
        if (user.getBirthday().isAfter(date)) {
            log.debug("Ошибка добавления пользователя. Некорректно введена дата рождения");
            throw new InvalidBirthdayExeption("InvalidBirthdayExeption");
        }
    }

    public User getUserByEmail(String email) {
        User user = userDbStorage.findUserByEmail(email);
        if (user == null) {
            throw new NotFoundUserException("пользователя нет в бд");
        }
        return userDbStorage.findUserByEmail(email);
    }

    public void deleteUser(String email) throws ParseException {
        userDbStorage.deleteUser(email);
    }

    public List<User> findAllUsers() {
        return userDbStorage.findAllUsers();
    }

    public User createUser(User user) throws ParseException {
        checkExeption(user);
        User userDb = userDbStorage.findUserByEmail(user.getEmail());
        if (userDb != null) {
            throw new UserAlreadyExistException("пользователь уже есть в бд");
        }
        return userDbStorage.createUser(user);
    }

    public User updateUser(User user) throws ParseException {
        checkExeption(user);
        User userDb = userDbStorage.findUserByEmail(user.getEmail());
        if (userDb == null) {
            throw new UserAlreadyExistException("пользователя нет в бд");
        }
        return userDbStorage.updateUser(user);
    }

    //    public List<User> findAllFriends(Integer userId) {
//        User user = getUserId(userId);
//        Set<Integer> friends = user.getFriends();
//        log.debug("Получен список друзей пользователя с id: {}", userId);
//        return userStorage.findAllUsers().stream().filter(x -> friends.contains(x.getId())).toList();
//    }
//
//    public void addFriend(Integer userFirstId, Integer userFriendId) {
//        User user1 = getUserId(userFirstId);
//        Set<Integer> friends1 = user1.getFriends();
//        friends1.add(userFriendId);
//
//        User user2 = getUserId(userFriendId);
//        Set<Integer> friends2 = user2.getFriends();
//        friends2.add(userFirstId);
//        log.debug("Пользователи с id {} и {} стали друзьями", userFirstId, userFriendId);
//    }
//public List<User> getCommonFriends(Integer userFirstId, Integer otherId) {
//    List<User> userFirstFriends = findAllFriends(userFirstId);
//    List<User> otherFriends = findAllFriends(otherId);
//    return userFirstFriends.stream()
//            .filter(otherFriends::contains).toList();
//}
}
