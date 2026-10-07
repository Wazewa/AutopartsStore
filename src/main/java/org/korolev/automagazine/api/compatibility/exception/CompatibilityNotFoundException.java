package org.korolev.automagazine.api.compatibility.exception;

import org.korolev.automagazine.api.exceptions.ResourceNotFoundException;

public class CompatibilityNotFoundException extends ResourceNotFoundException {
    public CompatibilityNotFoundException(String message) {
        super(message);
    }
}
