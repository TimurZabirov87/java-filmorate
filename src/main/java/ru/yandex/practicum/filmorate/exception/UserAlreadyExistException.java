package ru.yandex.practicum.filmorate.exception;

import java.io.IOException;

public class UserAlreadyExistException extends IOException {

    public UserAlreadyExistException() {
    }

    public UserAlreadyExistException(final String message) {
        super(message);
    }

    public UserAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
