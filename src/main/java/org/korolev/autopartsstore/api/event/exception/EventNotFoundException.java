package org.korolev.autopartsstore.api.event.exception;

import org.korolev.autopartsstore.api.exception.ResourceNotFoundException;

public class EventNotFoundException extends ResourceNotFoundException {
    public EventNotFoundException(String message) {
        super(message);
    }
}
