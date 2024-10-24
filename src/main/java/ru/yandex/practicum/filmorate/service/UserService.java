package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserDbStorage userDbStorage;

    @Autowired
    public UserService(UserDbStorage userDbStorage) {
        this.userDbStorage = userDbStorage;
    }

    public User getUserByEmail(String email) {
        return userDbStorage.findAllUsers().stream().filter(x -> x.getEmail().equals(email)).toList().get(0);
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

    public void deleteUser(String email) throws ParseException {
        userDbStorage.deleteUser(email);
    }

    public List<User> findAllUsers() {
        return userDbStorage.findAllUsers();
    }

    public User createUser(User user) throws ParseException {
        return userDbStorage.createUser(user);
    }

    public User updateUser(User user) throws ParseException {
        return userDbStorage.updateUser(user);
    }
}
