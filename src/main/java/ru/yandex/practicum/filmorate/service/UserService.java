package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
Создайте UserService, который будет отвечать за такие операции с пользователями,
как добавление в друзья, удаление из друзей, вывод списка общих друзей.
Пока пользователям не надо одобрять заявки в друзья — добавляем сразу.
То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.
 */
@Slf4j
@Service
public class UserService {

    private LocalDate today = LocalDate.now();

    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

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

    public User updateUser(User user) throws InvalidEmailException, ValidationException, InvalidIdException {
        validation(user);
        if(user.getName().isEmpty() || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        if(userStorage.getAllUsers().containsKey(user.getId())){
            userStorage.update(user);
            log.info("User " + user + "updated");
        } else {
            throw new InvalidIdException("User " + user.getId().intValue() + "not found");
        }
        return user;
    }

    public User createUser(@Valid @RequestBody User user) throws UserAlreadyExistException, ValidationException, InvalidEmailException {
        validation(user);
        if(userStorage.getAllUsers().containsKey(user.getId())){
            throw new UserAlreadyExistException("User with this "+user.getId()+" exist");
        }
        if(user.getName().isEmpty() || user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        userStorage.create(user);
        log.info("User " + user + "created");
        return user;
    }

    public User getUserById (Long id) throws UserNotFoundException {
        if (!userStorage.getAllUsers().containsKey(id)) {
            throw new UserNotFoundException("Пользователь с id: " + id + " не найден");
        }
        return userStorage.getUnitById(id);
    }

    public List<User> getUsersFriends (Long id) throws UserNotFoundException {
        List<User> friends = new ArrayList<>();
        if (!getUserById(id).getFriends().isEmpty()) {
            for (Long friendsId : (getUserById(id).getFriends())) {
                friends.add(getUserById(friendsId));
            }
        }
        return friends;
    }

    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers().values();
    }

    public void addToFriends (Long id1, Long id2) throws UserNotFoundException {
        if (!userStorage.getAllUsers().containsKey(id1)) {
            throw new UserNotFoundException("Пользователь с id: " + id1 + " не найден");
        }
        if (!userStorage.getAllUsers().containsKey(id2)) {
            throw new UserNotFoundException("Пользователь с id: " + id2 + " не найден");
        }
        userStorage.getUnitById(id1).getFriends().add(id2);
        userStorage.getUnitById(id2).getFriends().add(id1);
    }

    public void deleteFromFriends (Long id1, Long id2) throws UserNotFoundException {
        if (!userStorage.getAllUsers().containsKey(id1)) {
            throw new UserNotFoundException("Пользователь с id: " + id1 + " не найден");
        }
        if (!userStorage.getAllUsers().containsKey(id2)) {
            throw new UserNotFoundException("Пользователь с id: " + id2 + " не найден");
        }
        if (!userStorage.getAllUsers().get(id1).getFriends().contains(id2)) {
            throw new UserNotFoundException("Пользователь с id: " + id2 + " не найден  друзьях у пользователя с id: " + id1);
        }
        if (!userStorage.getAllUsers().get(id2).getFriends().contains(id1)) {
            throw new UserNotFoundException("Пользователь с id: " + id1 + " не найден  друзьях у пользователя с id: " + id2);
        }
        userStorage.getUnitById(id1).getFriends().remove(id2);
        userStorage.getUnitById(id2).getFriends().remove(id1);
    }

    public List<User> showCommonFriends (Long id1, Long id2) {
        List<User> commonFriends = new ArrayList<>();
        if (!userStorage.getAllUsers().get(id1).getFriends().isEmpty() && !userStorage.getAllUsers().get(id2).getFriends().isEmpty()) {
            for (Long id : userStorage.getAllUsers().get(id1).getFriends()) {
                if (userStorage.getAllUsers().get(id2).getFriends().contains(id)) {
                    commonFriends.add(userStorage.getUnitById(id));
                }
            }
        }
        return commonFriends;
    }
}
