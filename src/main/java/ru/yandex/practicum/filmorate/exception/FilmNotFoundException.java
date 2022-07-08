package ru.yandex.practicum.filmorate.exception;

import java.io.IOException;

public class FilmNotFoundException extends IOException {

    public FilmNotFoundException() {
    }

    public FilmNotFoundException(final String message) {
        super(message);
    }

    public FilmNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FilmNotFoundException(final Throwable cause) {
        super(cause);
    }
}
