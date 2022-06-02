package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
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
public class UserController {

    private final Map<Long, User> allUsers = new HashMap<>();
    private LocalDate today = LocalDate.now();
    private Long nextIdCounter = 0L;

    private Long getNextIdCounter() {
        nextIdCounter++;
        return nextIdCounter;
    }

    public void userValidation(User user) throws InvalidEmailException, ValidationException {
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


    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(allUsers.values());
    }


    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws InvalidEmailException, ValidationException {
        log.info("Запрос на обновление пользователя получен");
        userValidation(user);
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

    @PostMapping
    public User createUser(@Valid @RequestBody User user) throws InvalidEmailException, UserAlreadyExistException, ValidationException {
        log.info("Запрос на добавление пользователя получен");
        userValidation(user);
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
