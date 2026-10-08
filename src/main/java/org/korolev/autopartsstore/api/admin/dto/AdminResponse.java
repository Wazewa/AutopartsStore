package org.korolev.autopartsstore.api.admin.dto;

import java.time.Instant;

public record AdminResponse(
        Long id,
        String name,
        String surname,
        String patronymic,
        String email,
        Instant registeredAt,
        Integer accessLevel
) {}
