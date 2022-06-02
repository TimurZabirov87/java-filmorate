package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exception.InvalidEmailException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    Film film;
    FilmController filmController = new FilmController();


    @BeforeEach
    void createNewFilm() {
        film = Film.builder()
                .name("Любовь и голуби")
                .description("советская лирическая комедия, снятая в 1984 году на киностудии «Мосфильм» " +
                        "режиссёром Владимиром Меньшовым по сценарию, написанному театральным актёром Владимиром Гуркиным.")
                .releaseDate(LocalDate.of(1985, Month.JANUARY, 7))
                .duration(107)
                .build();
    }

    @Test
    void shouldThrowValidationExceptionWhenNameIsEmpty(){
        film.setName("");
        final IOException exception = assertThrows(
                ValidationException.class,
                () -> filmController.filmValidation(film));
        assertEquals("Film's name is empty or blank", exception.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenNameIsBlank(){
        film.setName(" ");
        final IOException exception = assertThrows(
                ValidationException.class,
                () -> filmController.filmValidation(film));
        assertEquals("Film's name is empty or blank", exception.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenDescriptionMoreThan200Characters(){
        film.setDescription("советская лирическая комедия, снятая в 1984 году на киностудии «Мосфильм» " +
                "режиссёром Владимиром Меньшовым по сценарию, написанному театральным актёром Владимиром Гуркиным. " +
                "Сценарий последнего основан на его же одноимённой пьесе, написанной в 1981 году");
        final IOException exception = assertThrows(
                ValidationException.class,
                () -> filmController.filmValidation(film));
        assertEquals("The description of the film should be no more than 200 characters", exception.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenReleaseDateTooEarly(){
        LocalDate localDate = LocalDate.of(1755, Month.JANUARY,12);
        film.setReleaseDate(localDate);
        final IOException exception = assertThrows(
                ValidationException.class,
                () -> filmController.filmValidation(film));
        assertEquals("The release date is too early", exception.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionWhenDurationLessThan1(){
        film.setDuration(0);
        final IOException exception = assertThrows(
                ValidationException.class,
                () -> filmController.filmValidation(film));
        assertEquals("The duration of the film should be more than 0", exception.getMessage());
        film.setDuration(-1);
        final IOException exception2 = assertThrows(
                ValidationException.class,
                () -> filmController.filmValidation(film));
        assertEquals("The duration of the film should be more than 0", exception2.getMessage());
    }

}