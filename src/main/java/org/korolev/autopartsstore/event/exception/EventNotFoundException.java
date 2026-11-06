package org.korolev.autopartsstore.event.exception;

import org.korolev.autopartsstore.common.exception.ResourceNotFoundException;

public class EventNotFoundException extends ResourceNotFoundException {
    public EventNotFoundException(String message) {
        super(message);
    }
}
