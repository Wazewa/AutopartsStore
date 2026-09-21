package org.korolev.automagazine.api.exceptions;

public class OrderProductNotFoundException extends ResourceNotFoundException {
    public OrderProductNotFoundException(String message) {
        super(message);
    }
}