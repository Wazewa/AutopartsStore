package org.korolev.autopartsstore.compatibility.dto;

public record CompatibilityFilter (
        String brand,
        String model,
        Integer year,
        Long productId
) {
}
