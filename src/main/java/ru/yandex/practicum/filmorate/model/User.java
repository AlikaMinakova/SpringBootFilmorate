package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Date;

@Data
public class User {
    @NonNull
    @EqualsAndHashCode.Exclude
    private int id;
    @NonNull
    private String email;
    @NonNull
    @EqualsAndHashCode.Exclude
    private String login;
    @NonNull
    @EqualsAndHashCode.Exclude
    private String name;
    @NonNull
    @EqualsAndHashCode.Exclude
    private Date birthday;

    public User(@NonNull String email, @NonNull String login, @NonNull String name, @NonNull Date birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
