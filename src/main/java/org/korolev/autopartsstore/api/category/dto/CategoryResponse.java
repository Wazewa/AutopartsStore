package org.korolev.autopartsstore.api.category.dto;

public record CategoryResponse(
        Long id,
        String name,
        String description
) {}
