package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Like {
    @NonNull
    private String film;
    @NonNull
    private String user;

    public Like(@NonNull String film, @NonNull String user) {
        this.film = film;
        this.user = user;
    }
}
