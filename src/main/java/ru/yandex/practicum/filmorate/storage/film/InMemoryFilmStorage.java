package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Unit;
import ru.yandex.practicum.filmorate.storage.InMemoryUnitStorage;

import java.time.LocalDate;
import java.time.Month;

/*
Создайте классы InMemoryFilmStorage и InMemoryUserStorage, имплементирующие новые интерфейсы,
и перенесите туда всю логику хранения, обновления и поиска объектов.
 */
@Component
public class InMemoryFilmStorage extends InMemoryUnitStorage<Film> {

}
