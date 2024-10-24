package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserDbStorage userDbStorage;

    public UserController(UserDbStorage userDbStorage) {
        this.userDbStorage = userDbStorage;
    }

    @GetMapping("")
    public List<User> findAllUsers() {
        return userDbStorage.findAllUsers();
    }

    @GetMapping("/{email}")
    public User getUserId(@PathVariable String email) throws ParseException {
        return userDbStorage.findUserByEmail(email);
    }

//    @PutMapping("/{id}/friends/{friendId}")
//    public void addFriendToUser(@PathVariable String id, @PathVariable String friendId) {
//        userService.addFriend(Integer.parseInt(id), Integer.parseInt(friendId));
//    }
//
//
//    @GetMapping("/{id}/friends")
//    public List<User> findAllFriends(@PathVariable String id) {
//        return userService.findAllFriends(Integer.parseInt(id));
//    }
//
//    @GetMapping("/{id}/friends/common/{otherId}")
//    public List<User> findAllFriends(@PathVariable String id, @PathVariable String otherId) {
//        return userService.getCommonFriends(Integer.parseInt(id), Integer.parseInt(otherId));
//    }

    @DeleteMapping("/{email}")
    public void deleteFriendToUser(@PathVariable String email) throws ParseException {
        userDbStorage.deleteUser(email);
    }

    @PostMapping("")
    public User createUser(@RequestBody User user) throws ParseException {
        return userDbStorage.createUser(user);
    }

    @PutMapping("")
    public User updateUser(@RequestBody User user) throws ParseException {
        return userDbStorage.updateUser(user);
    }
}
