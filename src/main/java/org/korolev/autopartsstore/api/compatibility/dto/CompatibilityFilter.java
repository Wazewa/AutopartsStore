package org.korolev.autopartsstore.api.compatibility.dto;

public record CompatibilityFilter (
        String brand,
        String model,
        Integer year,
        Long productId
) {
}
