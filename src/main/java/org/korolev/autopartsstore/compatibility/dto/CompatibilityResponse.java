package org.korolev.autopartsstore.compatibility.dto;

public record CompatibilityResponse (
    Long id,
    Long productId,
    String brand,
    String model,
    Integer yearStart,
    Integer yearEnd
) {}
