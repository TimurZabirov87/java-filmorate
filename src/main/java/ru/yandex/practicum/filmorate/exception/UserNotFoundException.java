package ru.yandex.practicum.filmorate.exception;

import java.io.IOException;

public class UserNotFoundException extends IOException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(final String message) {
        super(message);
    }

    public UserNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(final Throwable cause) {
        super(cause);
    }
}
