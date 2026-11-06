package org.korolev.autopartsstore.user.exception;

import org.korolev.autopartsstore.common.exception.ResourceAlreadyExistsException;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
