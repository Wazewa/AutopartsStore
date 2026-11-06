package org.korolev.autopartsstore.user.exception;

import org.korolev.autopartsstore.common.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
