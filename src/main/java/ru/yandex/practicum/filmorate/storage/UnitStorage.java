package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Unit;
import ru.yandex.practicum.filmorate.model.User;

public interface UnitStorage <T extends Unit>{
    T create (T t);

    T update (T t);

    T delete (T t);
}
