package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {

    Map<Long, User> getAllUsers();

    User getUnitById(Long id);


    User create(User user);

    User update(User user);

    User delete(User user);

}
