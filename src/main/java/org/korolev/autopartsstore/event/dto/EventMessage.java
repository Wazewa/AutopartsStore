package org.korolev.autopartsstore.event.dto;

import org.korolev.autopartsstore.event.entity.EventType;

import java.time.Instant;

public record EventMessage(
        Long eventId,
        EventType eventType,
        Long userId,
        Instant eventDate,
        Integer sessionId
        //,String payload
) { }
