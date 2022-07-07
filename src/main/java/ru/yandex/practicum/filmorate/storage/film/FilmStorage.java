package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.Map;

public interface FilmStorage {

    Map<Long, Film> getAllFilms();

    Film getUnitById(Long id);


    Film create(Film film);

    Film update(Film film);

    Film delete(Film film);

}
