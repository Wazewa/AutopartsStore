package org.korolev.autopartsstore.api.user.exception;

import org.korolev.autopartsstore.api.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
