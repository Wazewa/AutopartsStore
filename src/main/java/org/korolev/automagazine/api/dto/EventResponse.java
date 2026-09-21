package org.korolev.automagazine.api.dto;

import org.korolev.automagazine.api.entities.EventType;

import java.time.Instant;

public record EventResponse(
        Long id,
        EventType eventType,
        Integer sessionId,
        Long customerId,
        Instant eventDate
) {}
