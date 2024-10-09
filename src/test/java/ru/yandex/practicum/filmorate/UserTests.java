package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.user.InvalidEmailException;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
class UserTests {


    @Test
    void createCorrectUser() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 27);
        Date date = calendar.getTime();
        User user = new User("mail@mail.com", "login", "name", date);
        List<User> list = List.of(user);
        UserController userController = new UserController();
        userController.createUser(user);

        Assertions.assertIterableEquals(userController.findAllUsers(), list);
    }

    @Test
    void createIncorrectEmailUser() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 27);
        Date date = calendar.getTime();
        User user = new User("mailmail.com", "login", "name", date);
        User user2 = new User("", "login", "name", date);
        UserController userController = new UserController();

        try {
            userController.createUser(user);
        } catch (Exception e) {
            assertEquals("InvalidEmailException", e.getMessage());
        }
        try {
            userController.createUser(user2);
        } catch (Exception e) {
            assertEquals("InvalidEmailException", e.getMessage());
        }
    }

    @Test
    void createIncorrectLoginUser() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 27);
        Date date = calendar.getTime();
        User user = new User("mail@mail.com", "", "name", date);
        User user2 = new User("mail@mail.com", "login new", "name", date);
        UserController userController = new UserController();

        try {
            userController.createUser(user);
        } catch (Exception e) {
            assertEquals("InvalidLogin", e.getMessage());
        }
        try {
            userController.createUser(user2);
        } catch (Exception e) {
            assertEquals("InvalidLogin", e.getMessage());
        }
    }

    @Test
    void EmptyNameEqualsLogin() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 27);
        Date date = calendar.getTime();
        User user = new User("mail@mail.com", "login", "", date);
        UserController userController = new UserController();
        userController.createUser(user);
        assertEquals("login", user.getName());
    }

    @Test
    void IncorrectBirthday() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        int year = Year.now().getValue();
        calendar.set(year + 1, Calendar.NOVEMBER, 20);
        Date date = calendar.getTime();
        User user = new User("mail@mail.com", "login", "", date);
        UserController userController = new UserController();
        try {
            userController.createUser(user);
        } catch (Exception e) {
            assertEquals("InvalidBirthdayExeption", e.getMessage());
        }
    }

    @Test
    void UserAlreadyExists() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 27);
        Date date = calendar.getTime();
        User user = new User("mail@mail.com", "login", "Nick", date);
        User user2 = new User("mail@mail.com", "login2", "Nick2", date);
        UserController userController = new UserController();
        userController.createUser(user);
        try {
            userController.createUser(user2);
        } catch (Exception e) {
            assertEquals("user already exists", e.getMessage());
        }
    }

    @Test
    void UpdateUser() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 27);
        Date date = calendar.getTime();
        User user = new User("mail@mail.com", "login", "Nick", date);
        calendar.set(2022, Calendar.MARCH, 29);
        Date date2 = calendar.getTime();
        User user2 = new User("mail@mail.com", "login2", "Nick2", date2);
        UserController userController = new UserController();
        userController.createUser(user);
        userController.updateUser(user2);
        assertEquals(user2.getName(), userController.findAllUsers().get(0).getName());
        assertEquals(user2.getBirthday(), userController.findAllUsers().get(0).getBirthday());
        assertEquals(user2.getLogin(), userController.findAllUsers().get(0).getLogin());
    }


}
