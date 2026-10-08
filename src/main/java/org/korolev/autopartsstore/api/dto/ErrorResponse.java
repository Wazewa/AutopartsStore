package org.korolev.autopartsstore.api.dto;

import java.time.Instant;

public record ErrorResponse(
        int status,
        String error,
        String message,
        Instant timestamp,
        String path
) {
    public static ErrorResponse of(int status, String error, String message, Instant timestamp , String path) {
        return new ErrorResponse(status, error, message, timestamp , path);
    }
}
