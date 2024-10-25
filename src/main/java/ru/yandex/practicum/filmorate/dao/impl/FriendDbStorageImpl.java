package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendDbStorage;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Component
public class FriendDbStorageImpl implements FriendDbStorage {

    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userDbStorage;

    @Autowired
    public FriendDbStorageImpl(JdbcTemplate jdbcTemplate, UserDbStorage userDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDbStorage = userDbStorage;
    }

    @Override
    public List<User> getFriendsByEmail(String email) {
        String sql = "select * from friends where user_email = ?";
        log.info("получен список друзей пользователя {}", email);
        List<Friend> friends = jdbcTemplate.query(sql, this::makeFriend, email);
        return friends.stream().map(x -> userDbStorage.findUserByEmail(x.getFriend_email())).toList();
    }

    @Override
    public void addFriend(String user_email, String friend_email) {
        String sql = "select * from friends where user_email = ?";
        log.info("добавление в друзья пользователей {} и {}", user_email, friend_email);
        List<Friend> friends = jdbcTemplate.query(sql, this::makeFriend, friend_email);
        String sqlAdd = "insert into friends (user_email, friend_email, confirm) values (?, ?, ?)";
        if (!friends.isEmpty()) {
            Friend friend = friends.stream().filter(x -> x.getFriend_email().equals(user_email)).toList().get(0);
            if (friend == null) {
                jdbcTemplate.update(sqlAdd, user_email, friend_email, false);
            } else {
                jdbcTemplate.update(sqlAdd, user_email, friend_email, true);
                String sqlUpdate = "update friends set confirm = ? where user_email = ? and friend_email = ?";
                jdbcTemplate.update(sqlUpdate, true, friend_email, user_email);
            }
        } else {
            jdbcTemplate.update(sqlAdd, user_email, friend_email, false);
        }
    }

    @Override
    public void deleteFriend(String user_email, String friend_email) {
        String sql = "select * from friends where user_email = ?";
        // log.info("добавление в друзья пользователей {} и {}", user_email, user_email);
        List<Friend> friends = jdbcTemplate.query(sql, this::makeFriend, user_email);
        Friend friend = friends.stream().filter(x -> x.getFriend_email().equals(friend_email)).toList().get(0);
        if (friend != null) {
            String sqlDelete = "delete from friends where user_email = ? and friend_email = ?";
            jdbcTemplate.update(sqlDelete, user_email, friend_email);
            if (friend.isConfirm()) {
                String sqlUpdate = "update friends set confirm = ? where user_email = ? and friend_email = ?";
                jdbcTemplate.update(sqlUpdate, false, friend_email, user_email);
            }
            log.info("пользователь {} удалил из друзей пользователя {}", user_email, friend_email);
        }
    }

    public Friend makeFriend(ResultSet rs, int rowNum) throws SQLException {
        String user_email = rs.getString("user_email");
        String friend_email = rs.getString("friend_email");
        boolean confirm = rs.getString("confirm").equals("t");
        return new Friend(user_email, friend_email, confirm);
    }
}
