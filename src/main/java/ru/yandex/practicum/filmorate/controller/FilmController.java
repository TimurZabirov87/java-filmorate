package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.exception.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Unit;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends UnitController<Film> {

    private final Map<Long, Film> allFilms = new HashMap<>();
    private final LocalDate cinemasBirthday = LocalDate.of(1895, Month.DECEMBER, 28);
    private final static int MAX_DESCRIPTION_LENGTH = 200;
    protected Long nextIdCounter = 0L;

    protected Long getNextIdCounter() {
        nextIdCounter++;
        return nextIdCounter;
    }


    @Override
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

    @GetMapping
    @Override
    public List<Film> getAll() {
        return new ArrayList<>(allFilms.values());
    }


    @Override
    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws InvalidIdException, ValidationException {
        log.info("Запрос на обновление фильма получен");
        validation(film);
        if(allFilms.containsKey(film.getId())){
            allFilms.put(film.getId(), film);
            log.info("Film " + film + "updated");
        } else {
            throw new InvalidIdException("Film not found");
        }
        return film;
    }

    @Override
    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws FilmAlreadyExistException, ValidationException {
        log.info("Запрос на добавление фильма получен");
        validation(film);
        if(allFilms.containsKey(film.getId())){
            throw new FilmAlreadyExistException("Film with this id: "+film.getId()+" exist");
        }
        film.setId(getNextIdCounter());
        allFilms.put(film.getId(), film);
        log.info("Film " + film + "created");
        return film;
    }
}

