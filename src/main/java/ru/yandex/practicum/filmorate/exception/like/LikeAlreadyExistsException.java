package ru.yandex.practicum.filmorate.exception.like;

public class LikeAlreadyExistsException extends RuntimeException{

    public LikeAlreadyExistsException(final String message) {
        super(message);
    }
}