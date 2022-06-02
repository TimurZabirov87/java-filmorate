package ru.yandex.practicum.filmorate.exception;

import java.io.IOException;

public class InvalidIdException extends IOException {

    public InvalidIdException() {
    }

    public InvalidIdException(final String message) {
        super(message);
    }

    public InvalidIdException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidIdException(final Throwable cause) {
        super(cause);
    }
}
