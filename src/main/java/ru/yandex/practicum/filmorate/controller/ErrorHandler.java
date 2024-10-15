package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.film.*;
import ru.yandex.practicum.filmorate.exception.user.*;

import java.util.Map;

@RestControllerAdvice("ru.yandex.practicum.filmorate.controller")
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({FilmAlreadyExistExeption.class,
            IncorrectCountValueException.class,
            InvalidDescriptionExeption.class,
            InvalidDurationExeption.class,
            InvalidReleaseDateException.class,
            InvalidBirthdayExeption.class,
            InvalidEmailException.class,
            InvalidLoginExeption.class,
            InvalidNameExeption.class,
            UserAlreadyExistException.class
    })
    public Map<String, String> handleException_BAD_REQUEST(final RuntimeException exception) {
        return Map.of("error", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({FilmNotFoundException.class,
            NotFoundUserException.class
    })
    public Map<String, String> handleException_NOT_FOUND(final RuntimeException exception) {
        return Map.of("error", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler()
    public Map<String, String> handleException_INTERNAL_SERVER_ERROR(final RuntimeException exception) {
        return Map.of("error", exception.getMessage());
    }
}