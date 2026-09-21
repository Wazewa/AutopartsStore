package org.korolev.automagazine.api.exceptions;

public class AdminNotFoundException extends ResourceNotFoundException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}
