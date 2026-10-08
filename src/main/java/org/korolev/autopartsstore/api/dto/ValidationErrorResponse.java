package org.korolev.autopartsstore.api.dto;

import java.time.Instant;
import java.util.Map;

public record ValidationErrorResponse (
        int status,
        String error,
        String message,
        Instant timestamp,
        String path,
        Map<String, String> fieldErrors
) {
    public static ValidationErrorResponse of(int status, String error, String message, Instant timestamp , String path, Map<String, String> fieldErrors) {
        return new ValidationErrorResponse(status, error, message, timestamp, path, fieldErrors);
    }
}
