package ru.yandex.practicum.filmorate.exception;

import java.io.IOException;

public class FilmAlreadyExistException extends IOException {

    public FilmAlreadyExistException() {
    }

    public FilmAlreadyExistException(final String message) {
        super(message);
    }

    public FilmAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FilmAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}