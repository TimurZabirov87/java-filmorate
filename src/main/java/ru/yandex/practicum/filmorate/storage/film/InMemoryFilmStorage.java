package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.HashMap;
import java.util.Map;


@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Long nextIdCounter = 0L;
    private Map<Long, Film> allFilms = new HashMap<>();

    @Override
    public Map<Long, Film> getAllFilms() {
        return allFilms;
    }

    Long getNextIdCounter() {
        nextIdCounter++;
        return nextIdCounter;
    }

    public Film getUnitById(Long id) {
        return allFilms.get(id);
    }

    @Override
    public Film create(Film film) {
        film.setId(getNextIdCounter());
        allFilms.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        allFilms.put(film.getId(), film);
        return film;
    }

    @Override
    public Film delete(Film film) {
        allFilms.remove(film.getId());
        return film;
    }

}
