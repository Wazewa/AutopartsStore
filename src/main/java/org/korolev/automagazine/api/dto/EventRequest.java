package org.korolev.automagazine.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.korolev.automagazine.api.entities.EventType;

public record EventRequest(
        Long customerId,

        @NotNull(message = "Event type is required")
        EventType eventType,

        @NotNull(message = "Session ID is required")
        @Positive(message = "Session ID must be positive")
        Integer sessionId
) {}