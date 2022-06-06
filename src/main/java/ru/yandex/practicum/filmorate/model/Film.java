package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film extends Unit{

    @NotNull
    @NotBlank
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
}
