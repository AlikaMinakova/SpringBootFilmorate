package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.film.FilmAlreadyExistExeption;
import ru.yandex.practicum.filmorate.exception.film.InvalidDescriptionExeption;
import ru.yandex.practicum.filmorate.exception.film.InvalidDurationExeption;
import ru.yandex.practicum.filmorate.exception.film.InvalidReleaseDateException;
import ru.yandex.practicum.filmorate.exception.user.InvalidNameExeption;
import ru.yandex.practicum.filmorate.model.Film;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmTests {

    private static final Calendar calendar = Calendar.getInstance();

    static {
        calendar.set(2022, Calendar.MARCH, 27);
    }

    private final static Date date = calendar.getTime();

    @Test
    void createCorrectFilm() throws ParseException {
        Film film = new Film("film", "lalala", date, 20.0);
        FilmController filmController = new FilmController();
        filmController.createFilm(film);
        Assertions.assertIterableEquals(filmController.findAllFilms(), List.of(film));
    }

    @Test
    void createIncorrectNameFilm() {
        Film film = new Film("", "lalala", date, 20.0);
        FilmController filmController = new FilmController();
        assertThrows(InvalidNameExeption.class,
                () -> filmController.createFilm(film));
    }

    @Test
    void createIncorrectDescriptionFilm() throws ParseException {
        Film film = new Film("name", "" +
                "descriptiondescriptiondescriptiondescriptiondescriptiondescription" +
                "descriptiondescriptiondescriptiondescriptiondescriptiondescription" +
                "descriptiondescriptiondescriptiondescriptiondescriptiondescription" +
                "descriptiondescriptiondescriptiondescriptiondescriptiondescription",
                date, 20.0);
        FilmController filmController = new FilmController();
        assertThrows(InvalidDescriptionExeption.class,
                () -> filmController.createFilm(film));
    }

    @Test
    void createIncorrectRelizDateFilm() throws ParseException {
        calendar.set(1894, Calendar.MARCH, 27);
        Date date = calendar.getTime();

        Film film = new Film("name", "description", date, 20.0);
        FilmController filmController = new FilmController();
        assertThrows(InvalidReleaseDateException.class,
                () -> filmController.createFilm(film));
    }

    @Test
    void createIncorrectDurationFilm() throws ParseException {
        Film film = new Film("name", "", date, 0.0);
        FilmController filmController = new FilmController();
        assertThrows(InvalidDurationExeption.class,
                () -> filmController.createFilm(film));
    }

    @Test
    void FilmAlreadyExist() throws ParseException {
        Film film = new Film("name", "", date, 26.0);
        Film film2 = new Film("name", "2", date, 25.0);
        FilmController filmController = new FilmController();
        filmController.createFilm(film);
        assertThrows(FilmAlreadyExistExeption.class,
                () -> filmController.createFilm(film2));
    }

    @Test
    void UpdateFilm() throws ParseException {
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
