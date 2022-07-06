package ru.yandex.practicum.filmorate.controller;


import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Unit;
import ru.yandex.practicum.filmorate.model.User;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class UnitController<T extends Unit> {

    public abstract T update(T data) throws InvalidIdException, ValidationException, InvalidEmailException;

    public abstract T create(T data) throws FilmAlreadyExistException, ValidationException, UserAlreadyExistException, InvalidEmailException;

}
