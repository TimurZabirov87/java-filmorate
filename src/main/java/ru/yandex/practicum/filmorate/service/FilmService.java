package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

/*
Создайте FilmService, который будет отвечать за операции с фильмами,
— добавление и удаление лайка, вывод 10 наиболее популярных фильмов по количеству лайков.
Пусть пока каждый пользователь может поставить лайк фильму только один раз.
 */
@Slf4j
@Service
public class FilmService {

    private final LocalDate cinemasBirthday = LocalDate.of(1895, Month.DECEMBER, 28);
    private final static int MAX_DESCRIPTION_LENGTH = 200;

    private FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }


    public void validation(Film film) throws ValidationException {

        if(film.getName()==null || film.getName().isBlank()){
            throw new ValidationException("Film's name is empty or blank");
        }
        if(film.getDescription().length() > MAX_DESCRIPTION_LENGTH){
            throw new ValidationException("The description of the film should be no more than 200 characters");
        }
        if(film.getReleaseDate().isBefore(cinemasBirthday)){
            throw new ValidationException("The release date is too early");
        }
        if(film.getDuration() <= 0){
            throw new ValidationException("The duration of the film should be more than 0");
        }
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.getAllFilms().values();
    }

    public Film getFilmById (Long id) throws FilmNotFoundException {
        if (!filmStorage.getAllFilms().containsKey(id)) {
            throw new FilmNotFoundException("Фильм с id: " + id + " не найден");
        }
        return filmStorage.getAllFilms().get(id);
    }

    public Film updateFilm(Film film) throws InvalidIdException, ValidationException {
        validation(film);
        if(filmStorage.getAllFilms().containsKey(film.getId())){
            filmStorage.update(film);
            log.info("Film " + film + "updated");
        } else {
            throw new InvalidIdException("Film not found");
        }
        return film;
    }

    public Film createFilm(Film film) throws FilmAlreadyExistException, ValidationException {
        validation(film);
        if(filmStorage.getAllFilms().containsKey(film.getId())){
            throw new FilmAlreadyExistException("Film with this id: "+film.getId()+" exist");
        }
        filmStorage.create(film);
        log.info("Film " + film + " id: " + film.getId() + " created");
        return film;
    }

    public Film addLike(Long filmId, Long userId) throws FilmNotFoundException {
        if (!filmStorage.getAllFilms().containsKey(filmId)) {
            throw new FilmNotFoundException("Фильм с id: " + filmId + " не найден");
        }
        getFilmById(filmId).getLikes().add(userId);
        return filmStorage.getUnitById(filmId);
    }

    public Film removeLike(Long filmId, Long userId) throws InvalidIdException, FilmNotFoundException {
        if (!filmStorage.getAllFilms().containsKey(filmId)) {
            throw new FilmNotFoundException("Фильм с id: " + filmId + " не найден");
        }
        if (!filmStorage.getUnitById(filmId).getLikes().contains(userId)) {
            throw new InvalidIdException("Лайк от пользователя с id: " + userId + " для фильма с id: " + filmId + " не найден");
        }
        filmStorage.getUnitById(filmId).getLikes().remove(userId);
        return filmStorage.getUnitById(filmId);
    }

    public List<Film> getMostPopularFilms (int count) {
        List<Film> mostPopularFilmsList = new ArrayList<>(filmStorage.getAllFilms().values());
        mostPopularFilmsList.sort((f1, f2) -> f2.getLikes().size() - f1.getLikes().size());
        if (mostPopularFilmsList.size() < count) {
            return mostPopularFilmsList;
        } else {
            return mostPopularFilmsList.subList(0, count);
        }
    }

}
