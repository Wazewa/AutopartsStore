package org.korolev.automagazine.api.category.dto;

public record CategoryResponse(
        Long id,
        String name,
        String description
) {}
