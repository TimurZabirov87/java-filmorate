package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;


import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends UnitController<Film> {

    private FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping
    public Collection<Film> getAll() {
        return filmService.getAllFilms();
    }

    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws InvalidIdException, ValidationException {
        log.info("Запрос на обновление фильма получен");
        filmService.updateFilm(film);
        return film;
    }

    @Override
    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws FilmAlreadyExistException, ValidationException {
        log.info("Запрос на добавление фильма получен");
        filmService.createFilm(film);
        return film;
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable Long id) throws FilmNotFoundException {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike (@PathVariable Long id, @PathVariable Long userId) throws FilmNotFoundException {
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) throws FilmNotFoundException, InvalidIdException {
        return filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms (@RequestParam(defaultValue = "10") int count) {
        if (count < 1) {
            return filmService.getMostPopularFilms(10);
        } else {
            return filmService.getMostPopularFilms(count);
        }
    }

    @ExceptionHandler ({FilmNotFoundException.class, UserNotFoundException.class, InvalidIdException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFilmNotFound(final IOException e) {
        return e.getMessage();
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidation(final ValidationException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationErr(final javax.validation.ValidationException e) {
        return e.getMessage();
    }


    @ExceptionHandler({FilmAlreadyExistException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(final IOException e) {
        return e.getMessage();
    }


    /*
С помощью аннотации @PathVariable добавьте возможность получать
каждый фильм и данные о пользователях по их уникальному идентификатору: GET .../users/{id}.
Добавьте методы, позволяющие пользователям добавлять друг друга в друзья,
получать список общих друзей и лайкать фильмы. Проверьте, что все они работают корректно.
PUT /films/{id}/like/{userId} — пользователь ставит лайк фильму.
DELETE /films/{id}/like/{userId} — пользователь удаляет лайк.
GET /films/popular?count={count} — возвращает список из первых count фильмов по количеству лайков.
Если значение параметра count не задано, верните первые 10.
Убедитесь, что ваше приложение возвращает корректные HTTP-коды.
400 — если ошибка валидации: ValidationException;
404 — для всех ситуаций, если искомый объект не найден;
500 — если возникло исключение.
     */
}

