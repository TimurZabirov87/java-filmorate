package ru.yandex.practicum.filmorate.storage;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Unit;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class InMemoryUnitStorage<T extends Unit> implements UnitStorage<T> {

    protected Long nextIdCounter = 0L;
    protected final Map<Long, T> allUnits = new HashMap<>();

    protected Long getNextIdCounter() {
        nextIdCounter++;
        return nextIdCounter;
    }

    public T getUnitById(Long id) {
        return allUnits.get(id);
    }


    public T create(T t) {
        t.setId(getNextIdCounter());
        allUnits.put(t.getId(), t);
        return t;
    }

    public T update(T t) {
        allUnits.put(t.getId(), t);
        return t;
    }

    public T delete(T t) {
        allUnits.remove(t.getId());
        return t;
    }

}
