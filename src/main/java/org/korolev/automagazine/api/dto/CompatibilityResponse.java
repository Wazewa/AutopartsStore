package org.korolev.automagazine.api.dto;

public record CompatibilityResponse (
    Long id,
    Long productId,
    String brand,
    String model,
    Integer yearStart,
    Integer yearEnd
) {}
