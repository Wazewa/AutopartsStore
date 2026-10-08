package org.korolev.autopartsstore.api.event.dto;

import org.korolev.autopartsstore.api.event.entity.EventType;

import java.time.Instant;

public record EventResponse(
        Long id,
        EventType eventType,
        Integer sessionId,
        Long customerId,
        Instant eventDate
) {}
