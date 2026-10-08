package org.korolev.autopartsstore.api.compatibility.exception;

import org.korolev.autopartsstore.api.exceptions.ResourceNotFoundException;

public class CompatibilityNotFoundException extends ResourceNotFoundException {
    public CompatibilityNotFoundException(String message) {
        super(message);
    }
}
