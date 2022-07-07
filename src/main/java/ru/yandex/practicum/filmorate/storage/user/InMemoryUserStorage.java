package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

/*
Создайте классы InMemoryFilmStorage и InMemoryUserStorage, имплементирующие новые интерфейсы,
и перенесите туда всю логику хранения, обновления и поиска объектов.
*/

@Component
public class InMemoryUserStorage implements UserStorage{

    private Long nextIdCounter = 0L;
    private final Map<Long, User> allUsers = new HashMap<>();

    @Override
    public Map<Long, User> getAllUsers() {
        return allUsers;
    }

    Long getNextIdCounter() {
        nextIdCounter++;
        return nextIdCounter;
    }

    public User getUnitById(Long id) {
        return allUsers.get(id);
    }

    @Override
    public User create(User user) {
        user.setId(getNextIdCounter());
        allUsers.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        allUsers.put(user.getId(), user);
        return user;
    }

    @Override
    public User delete(User user) {
        allUsers.remove(user.getId());
        return user;
    }

}
