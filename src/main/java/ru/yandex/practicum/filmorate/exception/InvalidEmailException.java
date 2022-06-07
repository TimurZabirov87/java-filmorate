package ru.yandex.practicum.filmorate.exception;

import java.io.IOException;

public class InvalidEmailException extends IOException {

    public InvalidEmailException() {
    }

    public InvalidEmailException(final String message) {
        super(message);
    }

    public InvalidEmailException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailException(final Throwable cause) {
        super(cause);
    }
}
