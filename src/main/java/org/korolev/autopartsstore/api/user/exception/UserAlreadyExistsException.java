package org.korolev.autopartsstore.api.user.exception;

import org.korolev.autopartsstore.api.exception.ResourceAlreadyExistsException;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
