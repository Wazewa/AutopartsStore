package org.korolev.autopartsstore.api.event.dto;

import org.korolev.autopartsstore.api.event.entity.EventType;

import java.time.Instant;

public record EventMessage(
        Long eventId,
        EventType eventType,
        Long userId,
        Instant eventDate,
        Integer sessionId
        //,String payload
) { }
