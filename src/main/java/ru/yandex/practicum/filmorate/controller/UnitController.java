package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Unit;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

public abstract class UnitController<T extends Unit> {


    public abstract void validation(T t) throws ValidationException, InvalidEmailException;

    public abstract List<T> getAll();

    public abstract T update(T data) throws InvalidIdException, ValidationException, InvalidEmailException;

    public abstract T create(T data) throws FilmAlreadyExistException, ValidationException, UserAlreadyExistException, InvalidEmailException;

}
