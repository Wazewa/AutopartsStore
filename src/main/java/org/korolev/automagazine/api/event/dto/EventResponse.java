package org.korolev.automagazine.api.event.dto;

import org.korolev.automagazine.api.event.entity.EventType;

import java.time.Instant;

public record EventResponse(
        Long id,
        EventType eventType,
        Integer sessionId,
        Long customerId,
        Instant eventDate
) {}
