package org.korolev.autopartsstore.order.exception;

public class OrderNotModifiableException extends RuntimeException {
    public OrderNotModifiableException(String message) {
        super(message);
    }
}
