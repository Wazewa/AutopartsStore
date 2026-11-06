package org.korolev.autopartsstore.api.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.korolev.autopartsstore.api.event.entity.EventType;

@Schema(description = "Запрос на создание события")
public record EventRequest(
        @NotNull(message = "Event type is required")
        EventType eventType,

        @NotNull(message = "Session ID is required")
        @Positive(message = "Session ID must be positive")
        Integer sessionId
) {}