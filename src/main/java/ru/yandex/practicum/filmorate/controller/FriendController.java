package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendService;

import java.util.List;

@RestController
@RequestMapping("/friends")
@Slf4j
public class FriendController {
    private final FriendService friendService;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("/{user_id}")
    public List<User> getByEmail(@PathVariable String user_id) {
        return friendService.getFriendsByEmail(user_id);
    }

    @PostMapping("/{user_email}/{friend_email}")
    public void addFriend(@PathVariable String user_email, @PathVariable String friend_email) {
        friendService.addFriend(user_email, friend_email);
    }

    @DeleteMapping("/{user_id}/{friend_id}")
    public void getByEmail(@PathVariable String user_id, @PathVariable String friend_id) {
        friendService.deleteFriend(user_id, friend_id);
    }
}
