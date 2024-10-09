package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
class FilmTests {

    @Test
    void createCorrectFilm() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 27);
        Date date = calendar.getTime();

        Film film = new Film("film", "lalala", date, 20.0);
        FilmController filmController = new FilmController();
        filmController.createFilm(film);
        Assertions.assertIterableEquals(filmController.findAllFilms(), List.of(film));
    }

    @Test
    void createIncorrectNameFilm() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 27);
        Date date = calendar.getTime();

        Film film = new Film("", "lalala", date, 20.0);
        FilmController filmController = new FilmController();
        try {
            filmController.createFilm(film);
        } catch (Exception e) {
            Assertions.assertEquals("the name should not be empty", e.getMessage());
        }
    }

    @Test
    void createIncorrectDescriptionFilm() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 27);
        Date date = calendar.getTime();

        Film film = new Film("name", "" +
                "descriptiondescriptiondescriptiondescriptiondescriptiondescription" +
                "descriptiondescriptiondescriptiondescriptiondescriptiondescription" +
                "descriptiondescriptiondescriptiondescriptiondescriptiondescription",
                date, 20.0);
        FilmController filmController = new FilmController();
        try {
            filmController.createFilm(film);
        } catch (Exception e) {
            Assertions.assertEquals("the description should be no more than 200 characters long", e.getMessage());
        }
    }

    @Test
    void createIncorrectRelizDateFilm() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1894, Calendar.MARCH, 27);
        Date date = calendar.getTime();

        Film film = new Film("name", "", date, 20.0);
        FilmController filmController = new FilmController();
        try {
            filmController.createFilm(film);
        } catch (Exception e) {
            Assertions.assertEquals("the date should not be earlier than 28.12.1895", e.getMessage());
        }
    }

    @Test
    void createIncorrectDurationFilm() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 27);
        Date date = calendar.getTime();

        Film film = new Film("name", "", date, 0.0);
        FilmController filmController = new FilmController();
        try {
            filmController.createFilm(film);
        } catch (Exception e) {
            Assertions.assertEquals("the duration should not be <= 0", e.getMessage());
        }
    }

    @Test
    void FilmAlreadyExist() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 27);
        Date date = calendar.getTime();

        Film film = new Film("name", "", date, 26.0);
        Film film2 = new Film("name", "2", date, 25.0);
        FilmController filmController = new FilmController();
        filmController.createFilm(film);
        try {
            filmController.createFilm(film2);
        } catch (Exception e) {
            Assertions.assertEquals("the film already exists", e.getMessage());
        }
    }

    @Test
    void UpdateFilm() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 27);
        Date date = calendar.getTime();

        Film film = new Film("name", "", date, 26.0);
        Film film2 = new Film("name", "2", date, 25.0);
        FilmController filmController = new FilmController();
        filmController.createFilm(film);
        filmController.updateFilm(film2);
        Assertions.assertEquals(filmController.findAllFilms().get(0).getDescription(), film2.getDescription());
        Assertions.assertEquals(filmController.findAllFilms().get(0).getDuration(), film2.getDuration());
        Assertions.assertEquals(filmController.findAllFilms().get(0).getReleaseDate(), film2.getReleaseDate());
    }

}
