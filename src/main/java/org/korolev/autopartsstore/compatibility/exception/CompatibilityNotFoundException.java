package org.korolev.autopartsstore.compatibility.exception;

import org.korolev.autopartsstore.common.exception.ResourceNotFoundException;

public class CompatibilityNotFoundException extends ResourceNotFoundException {
    public CompatibilityNotFoundException(String message) {
        super(message);
    }
}
