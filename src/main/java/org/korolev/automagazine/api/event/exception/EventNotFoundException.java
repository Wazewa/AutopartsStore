package org.korolev.automagazine.api.event.exception;

import org.korolev.automagazine.api.exceptions.ResourceNotFoundException;

public class EventNotFoundException extends ResourceNotFoundException {
    public EventNotFoundException(String message) {
        super(message);
    }
}
