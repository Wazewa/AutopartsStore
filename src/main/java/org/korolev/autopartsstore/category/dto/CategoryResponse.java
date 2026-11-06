package org.korolev.autopartsstore.category.dto;

public record CategoryResponse(
        Long id,
        String nameCategory,
        String descriptionCategory
) {}
