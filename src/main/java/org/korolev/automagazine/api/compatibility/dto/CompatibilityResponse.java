package org.korolev.automagazine.api.compatibility.dto;

public record CompatibilityResponse (
    Long id,
    Long productId,
    String brand,
    String model,
    Integer yearStart,
    Integer yearEnd
) {}
