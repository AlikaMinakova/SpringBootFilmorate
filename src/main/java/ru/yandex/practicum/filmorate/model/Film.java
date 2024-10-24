package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class Film {
    @NonNull
    private String name;
    @NonNull
    @EqualsAndHashCode.Exclude
    private String description;
    @NonNull
    @EqualsAndHashCode.Exclude
    private LocalDate releaseDate;
    @NonNull
    @EqualsAndHashCode.Exclude
    private Double duration;

    public Film(@NonNull String name, @NonNull String description, @NonNull LocalDate releaseDate, @NonNull Double duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
