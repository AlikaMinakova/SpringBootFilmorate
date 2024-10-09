package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Date;

@Data
public class Film {
    @NonNull
    @EqualsAndHashCode.Exclude
    private int id;
    @NonNull
    private String name;
    @NonNull
    @EqualsAndHashCode.Exclude
    private String description;
    @NonNull
    @EqualsAndHashCode.Exclude
    private Date releaseDate;
    @NonNull
    @EqualsAndHashCode.Exclude
    private Double duration;

    public Film(@NonNull String name, @NonNull String description, @NonNull Date releaseDate, @NonNull Double duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
