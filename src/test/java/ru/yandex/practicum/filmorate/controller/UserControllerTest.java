package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exception.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    User user;
    UserController userController = new UserController();

    @BeforeEach
    void newUserCreate() {
        user = User.builder()
                .email("gagarin@kosmos.ru")
                .login("firstInSpace")
                .name("Yuri")
                .birthday(LocalDate.of(1934, Month.MARCH, 9))
                .build();
    }


    @Test
    void shouldThrowInvalidEmailExceptionWhenEmailIsEmpty(){
        user.setEmail("");
        final IOException exception = assertThrows(
                InvalidEmailException.class,
                () -> userController.userValidation(user));
        assertEquals("E-mail is empty or blank", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenEmailIsBlank(){
        user.setEmail(" ");
        final IOException exception = assertThrows(
                InvalidEmailException.class,
                () -> userController.userValidation(user));
        assertEquals("E-mail is empty or blank", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidEmailExceptionWhenEmailDontContentsAt(){
        user.setEmail("gagarin-kosmos.ru");
        final IOException exception = assertThrows(
                InvalidEmailException.class,
                () -> userController.userValidation(user));
        assertEquals("Incorrect e-mail", exception.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenLoginIsBlank(){
        user.setLogin("");
        final IOException exception = assertThrows(
                ValidationException.class,
                () -> userController.userValidation(user));
        assertEquals("The login must not be empty and contain spaces", exception.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenLoginContentsSpaces(){
        user.setLogin("first In Space");
        final IOException exception = assertThrows(
                ValidationException.class,
                () -> userController.userValidation(user));
        assertEquals("The login must not be empty and contain spaces", exception.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenBirthdayAfterToday(){
        LocalDate localDate = LocalDate.now().plusDays(2);
        user.setBirthday(localDate);
        final IOException exception = assertThrows(
                ValidationException.class,
                () -> userController.userValidation(user));
        assertEquals("Incorrect birthday", exception.getMessage());
    }

}