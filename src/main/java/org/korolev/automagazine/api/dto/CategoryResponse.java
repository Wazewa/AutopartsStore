package org.korolev.automagazine.api.dto;

public record CategoryResponse(
        Long id,
        String name,
        String description
) {}
