package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Like {
    @NonNull
    private int id;
    @NonNull
    private String user_email;

    public Like(@NonNull int id, @NonNull String user_email) {
        this.id = id;
        this.user_email = user_email;
    }
}
