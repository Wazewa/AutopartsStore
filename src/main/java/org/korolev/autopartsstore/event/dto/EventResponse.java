package org.korolev.autopartsstore.event.dto;

import org.korolev.autopartsstore.event.entity.EventType;

import java.time.Instant;

public record EventResponse(
        Long id,
        EventType eventType,
        Integer sessionId,
        Long userId,
        Instant eventDate
) {}
