package org.korolev.automagazine.api.exceptions;

public class CompatibilityNotFoundException extends ResourceNotFoundException {
    public CompatibilityNotFoundException(String message) {
        super(message);
    }
}
