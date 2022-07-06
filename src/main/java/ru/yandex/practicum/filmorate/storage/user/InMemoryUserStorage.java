package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Unit;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUnitStorage;

/*
Создайте классы InMemoryFilmStorage и InMemoryUserStorage, имплементирующие новые интерфейсы,
и перенесите туда всю логику хранения, обновления и поиска объектов.
*/

@Component
public class InMemoryUserStorage extends InMemoryUnitStorage<User>{


}
