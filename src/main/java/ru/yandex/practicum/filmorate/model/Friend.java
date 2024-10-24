package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;


@Data
public class Friend {
    @NonNull
    private String user_email;
    @NonNull
    private String friend_email;
    @NonNull
    private boolean confirm;

    public Friend(@NonNull String user_email, @NonNull String friend_email, @NonNull boolean confirm) {
        this.user_email = user_email;
        this.friend_email = friend_email;
        this.confirm = confirm;
    }
}
