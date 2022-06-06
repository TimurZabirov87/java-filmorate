package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Unit;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends UnitController<User>{

    private final Map<Long, User> allUsers = new HashMap<>();
    private LocalDate today = LocalDate.now();
    protected Long nextIdCounter = 0L;

    protected Long getNextIdCounter() {
        nextIdCounter++;
        return nextIdCounter;
    }

    @Override
    public void validation(User user) throws InvalidEmailException, ValidationException {

        if(user.getEmail()==null || user.getEmail().isBlank()){
            throw new InvalidEmailException("E-mail is empty or blank");
        }
        if(!user.getEmail().contains("@")){
            throw new InvalidEmailException("Incorrect e-mail");
        }
        if(user.getLogin().contains(" ") || user.getLogin().isBlank()){
            throw new ValidationException("The login must not be empty and contain spaces");
        }
        if(user.getBirthday().isAfter(today)){
            throw new ValidationException("Incorrect birthday");
        }
    }

    @Override
    @GetMapping
    public List<User> getAll (){
        return new ArrayList<>(allUsers.values());
    }


    @Override
    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException, InvalidEmailException {
        log.info("Запрос на обновление пользователя получен");
        validation(user);
        if(user.getName().isEmpty() || user.getName().isBlank()){
            user.setName(user.getLogin());
        }

        if(allUsers.containsKey(user.getId())){
            allUsers.put(user.getId(), user);
            log.info("User " + user + "updated");
        } else {
            throw new InvalidEmailException("User " + user.getId().intValue() + "not found");
        }
        return user;
    }

    @Override
    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException, UserAlreadyExistException, InvalidEmailException {
        log.info("Запрос на добавление пользователя получен");
        validation(user);
        if(allUsers.containsKey(user.getId())){
            throw new UserAlreadyExistException("User with this "+user.getId()+" exist");
        }
        if(user.getName().isEmpty() || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        user.setId(getNextIdCounter());
        allUsers.put(user.getId(), user);
        log.info("User " + user + "created");
        return user;
    }
}
