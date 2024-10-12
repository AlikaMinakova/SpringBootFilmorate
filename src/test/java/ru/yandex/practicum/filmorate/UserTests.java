package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.user.*;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
class UserTests {

    private static final Calendar calendar = Calendar.getInstance();

    static {
        calendar.set(2022, Calendar.MARCH, 27);
    }

    private final static Date date = calendar.getTime();


    @Test
    void createCorrectUser() throws ParseException {
        User user = new User("mail@mail.com", "login", "name", date);
        UserController userController = new UserController();
        userController.createUser(user);

        Assertions.assertIterableEquals(userController.findAllUsers(), List.of(user));
    }

    @Test
    void createIncorrectEmailUser() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 27);
        Date date = calendar.getTime();
        User user = new User("mailmail.com", "login", "name", date);
        User user2 = new User("", "login", "name", date);
        UserController userController = new UserController();
        assertThrows(InvalidEmailException.class,
                () -> userController.createUser(user));
        assertThrows(InvalidEmailException.class,
                () -> userController.createUser(user2));
    }

    @Test
    void createIncorrectLoginUser() {
        User user = new User("mail@mail.com", "", "name", date);
        User user2 = new User("mail@mail.com", "login new", "name", date);
        UserController userController = new UserController();
        assertThrows(InvalidLoginExeption.class,
                () -> userController.createUser(user));
        assertThrows(InvalidLoginExeption.class,
                () -> userController.createUser(user2));
    }

    @Test
    void EmptyNameEqualsLogin() throws ParseException {
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
        assertThrows(InvalidBirthdayExeption.class,
                () -> userController.createUser(user));
    }

    @Test
    void UserAlreadyExists() throws ParseException {
        User user = new User("mail@mail.com", "login", "Nick", date);
        User user2 = new User("mail@mail.com", "login2", "Nick2", date);
        UserController userController = new UserController();
        userController.createUser(user);
        assertThrows(UserAlreadyExistException.class,
                () -> userController.createUser(user2));
    }

    @Test
    void UpdateUser() throws ParseException {
        User user = new User("mail@mail.com", "login", "Nick", date);
        Calendar calendar = Calendar.getInstance();
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
