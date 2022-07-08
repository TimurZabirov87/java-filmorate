package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends UnitController<User>{

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public Collection<User> getAll() {
        return userService.getAllUsers();
    }

    @Override
    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException, InvalidEmailException, InvalidIdException {
        log.info("Запрос на обновление пользователя получен");
        userService.updateUser(user);
        return user;
    }

    @Override
    @PostMapping
    public User create(@Valid @RequestBody User user) throws ValidationException, UserAlreadyExistException, InvalidEmailException {
        log.info("Запрос на добавление пользователя получен");
        userService.createUser(user);
        return user;
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @PutMapping ("/{id}/friends/{friendId}")
    public void addToFriend (@PathVariable Long id, @PathVariable Long friendId) throws UserNotFoundException {
        userService.addToFriends(id, friendId);
    }

    @DeleteMapping ("/{id}/friends/{friendId}")
    public void removeFromFriends (@PathVariable Long id, @PathVariable Long friendId) throws UserNotFoundException {
        userService.deleteFromFriends(id, friendId);
    }

    @GetMapping ("/{id}/friends")
    public List<User> getFriends (@PathVariable Long id) throws UserNotFoundException {
        return userService.getUsersFriends(id);
    }

    @GetMapping ("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends (@PathVariable Long id, @PathVariable Long otherId) {
        return userService.showCommonFriends(id, otherId);
    }

    @ExceptionHandler ({InvalidIdException.class, FilmNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFilmNotFound(final IOException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidation(final ValidationException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationErr(final javax.validation.ValidationException e) {
        return e.getMessage();
    }


    @ExceptionHandler({UserAlreadyExistException.class, InvalidEmailException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(final IOException e) {
        return e.getMessage();
    }



    /*
C помощью аннотации @PathVariable добавьте возможность получать каждый фильм и данные о пользователях
по их уникальному идентификатору: GET .../users/{id}.
Добавьте методы, позволяющие пользователям добавлять друг друга в друзья, получать список общих друзей и лайкать фильмы. Проверьте, что все они работают корректно.
PUT /users/{id}/friends/{friendId} — добавление в друзья.
DELETE /users/{id}/friends/{friendId} — удаление из друзей.
GET /users/{id}/friends — возвращаем список пользователей, являющихся его друзьями.
GET /users/{id}/friends/common/{otherId} — список друзей, общих с другим пользователем.
Убедитесь, что ваше приложение возвращает корректные HTTP-коды.
400 — если ошибка валидации: ValidationException;
404 — для всех ситуаций, если искомый объект не найден;
500 — если возникло исключение.
     */
}
